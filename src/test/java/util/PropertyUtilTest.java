package util;


import mr.demonid.config.Config;
import mr.demonid.config.ConfigDefaults;
import mr.demonid.util.PropertyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Тестирование PropertyUtils.
 */
public class PropertyUtilTest {

    Config config;


    @BeforeEach
    void setUp() {
        config = ConfigDefaults.createDefaults();
    }


    @Test
    @DisplayName("Тест чтения поля")
    void testPropertyUtilsNestedPath() throws Exception {
        // Чтение
        String fontName = (String) PropertyUtils.getProperty(
                config, "viewConfig.chart.fontName");
        Float width = (Float) PropertyUtils.getProperty(
                config, "viewConfig.pressureTable.width");
        Color color = (Color) PropertyUtils.getProperty(config, "viewConfig.colors.text");
        // Проверка
        assertEquals(config.getViewConfig().getChart().getFontName(), fontName);
        assertEquals(config.getViewConfig().getPressureTable().getWidth(), width);
        assertEquals(config.getViewConfig().getColors().getText(), color);
    }

    @Test
    @DisplayName("Тест изменения поля")
    void testPropertyUtilsGetSet() throws Exception {
        // Изменение значения и проверка
        PropertyUtils.setProperty(config, "viewConfig.pressureTable.width", 0.12f);
        Float width = (Float) PropertyUtils.getProperty
                (config, "viewConfig.pressureTable.width");
        assertEquals(0.12f, width);
    }


}
