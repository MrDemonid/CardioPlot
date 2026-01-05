package params;

import com.beust.jcommander.ParameterException;
import mr.demonid.cli.args.AppParams;
import mr.demonid.cli.args.ParamBuilder;
import mr.demonid.cli.args.help.impl.HelpAnsi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class ParamsTest {

    ParamBuilder paramBuilder;
    
    @BeforeEach
    void setUp() {
        paramBuilder = new ParamBuilder(getClass().getSimpleName(), new HelpAnsi());
    }

    @Nested
    @DisplayName("Базовые операции")
    class Base {

        @Test
        @DisplayName("Параметры не заданы")
        void testEmptyArgsUsesDefaultConfig() {
            String[] args = {};
            Optional<AppParams> result = paramBuilder.parse(args);
            // ожидаем установки дефолтных значений
            assertTrue(result.isPresent());
            assertEquals("default.yml", result.get().getConfigFile());
            assertFalse(result.get().isHelp());
        }

        /**
         * Ожидаем исключения для неверного параметра,
         * поскольку используется опция: acceptUnknownOptions(false)
         */
        @Test
        @DisplayName("Некорректный аргумент")
        void testInvalidArgThrowsException() {
            String[] args = {"--unknown-option"};

            ParameterException thrown = assertThrows(ParameterException.class, () -> paramBuilder.parse(args));
            assertTrue(thrown.getMessage().contains("unknown-option"),
                    "Сообщение должно содержать имя некорректного аргумента");
        }

    }


    @Nested
    @DisplayName("Файл конфигурации")
    class ConfigFile {

        @Test
        @DisplayName("Задан файл конфигурации")
        void testConfigFileArgParsedCorrectly() {
            String[] args = {"--config-file", "custom.yml"};
            Optional<AppParams> result = paramBuilder.parse(args);

            assertTrue(result.isPresent());
            assertEquals("custom.yml", result.get().getConfigFile());
        }

        @Test
        @DisplayName("Короткая форма файла конфигурации")
        void testShortConfigArgParsed() {
            String[] args = {"-c", "alt.yml"};
            Optional<AppParams> result = paramBuilder.parse(args);

            assertTrue(result.isPresent());
            assertEquals("alt.yml", result.get().getConfigFile());
        }

        /**
         * Проверяем корректность передачи абсолютного пути к файлу.
         */
        @Test
        @DisplayName("Абсолютный путь к конфигу")
        void testAbsoluteConfigPath() {
            String[] args = {"--config-file", "/etc/app/config.yml"};
            Optional<AppParams> result = paramBuilder.parse(args);

            assertTrue(result.isPresent());
            assertEquals("/etc/app/config.yml", result.get().getConfigFile());
        }

        /**
         * Проверяем корректность передачи относительного пути к файлу".
         */
        @Test
        @DisplayName("Относительный путь к конфигу")
        void testRelativeConfigPath() {
            String[] args = {"--config-file", "../configs/config.yml"};
            Optional<AppParams> result = paramBuilder.parse(args);

            assertTrue(result.isPresent());
            assertEquals("../configs/config.yml", result.get().getConfigFile());
        }

        /**
         * Пустое имя файла должно быть заменено на дефолтное.
         * Может быть это и лишне, и нарушает принцип единой ответственности,
         * но мне так показалось удобнее.
         */
        @Test
        @DisplayName("Пустое имя файла конфигурации")
        void testEmptyConfigFileValueUsesDefault() {
            String[] args = {"--config-file", ""};
            Optional<AppParams> result = paramBuilder.parse(args);

            assertTrue(result.isPresent());
            assertEquals("default.yml", result.get().getConfigFile());
        }

        /**
         * По идее в именах файлов могут встречаться пробелы и мы
         * должны получить имя такого файла без искажений.
         */
        @Test
        @DisplayName("Имя файла конфигурации с пробелом")
        void testConfigFileWithSpaces() {
            String[] args = {"--config-file", "my config.yml"};
            Optional<AppParams> result = paramBuilder.parse(args);

            assertTrue(result.isPresent());
            assertEquals("my config.yml", result.get().getConfigFile());
        }

        /**
         * Ожидаем исключения, поскольку запретили переопределение
         * параметров опцией: allowParameterOverwriting(false).
         */
        @Test
        @DisplayName("Несколько --config-file")
        void testMultipleConfigFileArgs() {
            String[] args = {"--config-file", "first.yml", "--config-file", "second.yml"};

            assertThrows(ParameterException.class, () -> paramBuilder.parse(args));
        }

        /**
         * Ожидаем исключения, поскольку запретили переопределение
         * параметров опцией: allowParameterOverwriting(false).
         */
        @Test
        @DisplayName("Комбинация -c и --config-file")
        void testShortAndLongConfigCombination() {
            String[] args = {"-c", "short.yml", "--config-file", "long.yml"};

            assertThrows(ParameterException.class, () -> paramBuilder.parse(args));
        }

    }


    @Nested
    @DisplayName("Справка")
    class HelpTest {
        @ParameterizedTest(name = "Проверка флага справки: {0}")
        @ValueSource(strings = {"--help", "-h"})
        void testAllHelpForms(String helpArg) {
            Optional<AppParams> result = paramBuilder.parse(new String[]{helpArg});

            assertFalse(result.isPresent());
        }

        /**
         * Должна вывестись справка и вернуться пустой Optional,
         * с игнорированием других параметров.
         */
        @Test
        @DisplayName("Справка и другие параметры")
        void testHelpOverridesOtherArgs() {
            String[] args = {"--help", "--config-file", "ignored.yml"};
            Optional<AppParams> result = paramBuilder.parse(args);

            assertFalse(result.isPresent());
        }

        /**
         * Должна вывестись справка и вернуться пустой Optional,
         * с игнорированием других параметров.
         */
        @Test
        @DisplayName("Справка после других параметров")
        void testHelpAtTheEnd() {
            String[] args = {"--config-file", "ignored.yml", "--help"};
            Optional<AppParams> result = paramBuilder.parse(args);

            assertFalse(result.isPresent());
        }

        /**
         * Одиночные флаги ничего не переопределяют, поэтому на них
         * не влияет опция: allowParameterOverwriting(false).
         */
        @Test
        @DisplayName("Справка с повторениями")
        void testManyHelpArgs() {
            String[] args = {"--help", "-h", "--help"};
            Optional<AppParams> result = paramBuilder.parse(args);

            assertFalse(result.isPresent());
        }

        /**
         * Флаги чувствительны к регистру, поэтому данные воспримутся как
         * неизвестные флаги и будет выброшено исключение, поскольку
         * мы запретили их опцией: acceptUnknownOptions(false).
         */
        @DisplayName("Некорректный регистр флага справки")
        @ParameterizedTest(name = "флаг: {0}")
        @ValueSource(strings = {"-H", "--HELP"})
        void testHelpCaseSensitivity(String invalidHelpArg) {
            assertThrows(ParameterException.class, () -> paramBuilder.parse(new String[]{invalidHelpArg}));
        }

    }

}
