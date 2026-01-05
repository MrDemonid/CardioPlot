package mr.demonid.view.fonts.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class AwtFontProviderImpl implements mr.demonid.view.fonts.AwtFontProvider {

    private final Logger log = LogManager.getLogger(getClass().getName());

    private final Map<String, Font> fontCache = new ConcurrentHashMap<>();
    private final Map<FontKey, Font> derivedCache = new ConcurrentHashMap<>();


    @Override
    public Font getFont(String fontName, float size, int style) {
        if (style != Font.PLAIN && style != Font.BOLD && style != Font.ITALIC) {
            style = Font.PLAIN;
        }
        FontKey key = new FontKey(fontName, size, style);
        int finalStyle = style;
        return derivedCache.computeIfAbsent(key, k -> {
            Font base = fontCache.computeIfAbsent(fontName, this::loadFont);
            return base.deriveFont(finalStyle, size);
        });
    }


    private Font loadFont(String resource) {
        try (InputStream is = getClass().getResourceAsStream(resource)) {
            if (is != null) {
                Font base = Font.createFont(Font.TRUETYPE_FONT, is);
                log.info("Loaded font '{}'.", resource);
                return base;
            }
            log.error("File '{}' not found", resource);

        } catch (Exception e) {
            log.error("Can't load font file '{}'", e.getMessage());
        }
        return fallback();
    }


    /*
     * Попытка вернуть хотя бы системный предустановленный фонт.
     */
    private Font fallback() {
        // Пробуем загрузить системный "Arial"
        Font fallback = new Font("Arial", Font.PLAIN, 12);
        if (fallback.getFamily().equalsIgnoreCase("Arial")) {
            log.info("Font 'Arial' loaded.");
            return fallback;
        }
        fallback = new Font("Monospaced", Font.PLAIN, 12);
        if (fallback.getFamily().equalsIgnoreCase("Monospaced")) {
            return fallback;
        }
        // Этот точно будет
        return new Font("SansSerif", Font.PLAIN, 12);
    }

}
