package config;


import mr.demonid.config.Config;
import mr.demonid.config.ConfigDefaults;
import mr.demonid.config.ConfigPostProcessor;
import mr.demonid.config.categories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ConfigTest {

    Config defaultConfig;

    @BeforeEach
    void setUp() {
        defaultConfig = ConfigDefaults.createDefaults();
    }


    @Nested
    @DisplayName("Чтения YML-файла")
    class LoadYaml {

        @Test
        @DisplayName("Загрузка из не существующего файла")
        void fileNotFound() {
            Config config = new Config("non-existent.yml");
            // Проверяем, должны загрузиться дефолтные настройки
            assertEquals(defaultConfig, config);
        }

        @Test
        @DisplayName("Загрузка из файла неполного контента")
        void successLoad() throws IOException {
            String yamlContent = """
                    app:
                      name: "Test App"
                      source: "test.xlsx"
                      destination: "test.pdf"
                    view-config:
                      title:
                        font-size: 20
                      margin:
                        left: 50
                    """;

            Path tempFile = Files.createTempFile("test", ".yml");
            Files.writeString(tempFile, yamlContent);
            System.out.println("temp file: " + tempFile);

            Config config = new Config(tempFile.toString());

            assertEquals("Test App", config.getApp().getName());
            assertEquals("test.pdf", config.getApp().getDestination());
            assertEquals(20, config.getViewConfig().getTitle().getFontSize());
            assertEquals(50, config.getViewConfig().getMargin().getLeft());
            assertEquals(defaultConfig.getViewConfig().getTitle().getFontName(), config.getViewConfig().getTitle().getFontName());
            assertEquals(defaultConfig.getApp().getSource(), config.getApp().getSource());
            assertEquals(defaultConfig.getViewConfig().getMargin().getTop(), config.getViewConfig().getMargin().getTop());
            assertEquals(defaultConfig.getViewConfig().getPressureTable(), config.getViewConfig().getPressureTable());
            assertEquals(defaultConfig.getViewConfig().getChart(), config.getViewConfig().getChart());
            assertEquals(defaultConfig.getViewConfig().getPulseTable(), config.getViewConfig().getPulseTable());
            assertEquals(defaultConfig.getViewConfig().getColors(), config.getViewConfig().getColors());
            Files.delete(tempFile);
        }

        @Test
        @DisplayName("Чтение пустого файла")
        void testEmptyFile() throws Exception {
            // Пустой YAML
            Path emptyFile = Files.createTempFile("test", ".yml");
            Files.writeString(emptyFile, "  ");
            Config config = new Config(emptyFile.toString());

            // Проверяем, что загружены дефолты
            assertEquals(defaultConfig, config);
            Files.delete(emptyFile);
        }

        @Test
        @DisplayName("Файл с ошибками синтаксиса")
        void testInvalidSyntaxFile() throws Exception {
            // отсутствие закрывающей кавычки
            String yamlContent = """
                    app:
                      name: "Test App"
                      source: "test.xlsx"
                      destination: "test.pdf
                    view-config:
                      title:
                        font-size: 20
                      margin:
                        left: 50
                    """;
            Path tempFile = Files.createTempFile("test", ".yml");
            Files.writeString(tempFile, yamlContent);
            Config config = new Config(tempFile.toString());

            assertEquals(defaultConfig, config);
            Files.delete(tempFile);
        }

        @Test
        @DisplayName("Файл с несуществующей переменной")
        void testInvalidVarFile() throws Exception {
            // Несуществующее поле
            String yamlContent = """
                    app:
                      name: "Test App"
                      source: "test.xlsx"
                      destination: "test.pdf"
                    view-config:
                      title:
                        font-size: 20
                      margin:
                        left: 50
                        center: 13
                    """;
            Path tempFile = Files.createTempFile("test", ".yml");
            Files.writeString(tempFile, yamlContent);
            Config config = new Config(tempFile.toString());

            assertEquals(defaultConfig, config);
            Files.delete(tempFile);
        }

    }


    @Nested
    @DisplayName("Валидация и корректировка")
    class CheckYaml {

        Config config;

        @BeforeEach
        void setUp() {
            config = ConfigDefaults.createDefaults();
        }

        @Test
        @DisplayName("Отсутствие секции")
        void nullSection() throws Exception {
            // Удаляем одну секцию
            config.setApp(null);
            // Проверяем, что заменена на дефолтную
            doAssert(config);
        }

        @Test
        @DisplayName("Невалидные данные")
        void testNestedValidation() throws Exception {
            // Конфиг с пустышками
            ViewConfig viewConfig = new ViewConfig();
            viewConfig.setTitle(new TitleCfg());
            viewConfig.setPulseTable(new PulseTable());
            viewConfig.setPressureTable(new PressureTable());
            viewConfig.setChart(new ChartCfg());
            viewConfig.setColors(new ColorsCfg());
            viewConfig.setMargin(new MarginCfg());
            config.setViewConfig(viewConfig);

            App app = new App();
            app.setName(defaultConfig.getApp().getName());  // это необязательное поле
            app.setSource("");
            app.setDestination("");
            config.setApp(app);

            doAssert(config);
        }

        @Test
        @DisplayName("Null-данные")
        void testNullData() throws Exception {
            ViewConfig viewConfig = new ViewConfig();
            viewConfig.setTitle(null);
            viewConfig.setPulseTable(null);
            viewConfig.setPressureTable(null);
            viewConfig.setChart(null);
            viewConfig.setColors(null);
            viewConfig.setMargin(null);
            config.setViewConfig(viewConfig);

            App app = new App();
            app.setName(null);
            app.setSource(null);
            app.setDestination(null);
            config.setApp(app);

            doAssert(config);
        }

        private void doAssert(Config config) throws Exception {
            try (ConfigPostProcessor processor = new ConfigPostProcessor()) {
                Config normalized = processor.normalize(config);
                // После нормализации должно быть дефолтное значение
                assertNotNull(normalized);
                assertNotNull(normalized.getApp());
                assertNotNull(normalized.getViewConfig());
                assertEquals(defaultConfig.getApp(), normalized.getApp());
                assertEquals(defaultConfig.getViewConfig(), normalized.getViewConfig());
            }
        }


    }
}
