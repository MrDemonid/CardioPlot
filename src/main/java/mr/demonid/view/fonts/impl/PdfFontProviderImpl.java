package mr.demonid.view.fonts.impl;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import mr.demonid.model.pdf.impl.TitleRenderer;
import mr.demonid.view.fonts.PdfFontProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PdfFontProviderImpl implements PdfFontProvider {

    private final Logger log = LogManager.getLogger(getClass().getName());
    private final Map<String, BaseFont> cache = new ConcurrentHashMap<>();


    @Override
    public Font getFont(String fontName, float size, int style) {
        BaseFont bf = cache.get(fontName);
        if (bf == null) {
            bf = load(fontName);
            cache.put(fontName, bf);
        }
        return new Font(bf, size, style);
    }

    /**
     * Создает фонт из ресурса.
     * @param resource Имя файла фонта в папке resource.
     * @return Фонт или null.
     */
    private BaseFont load(String resource) {
        try (InputStream is = TitleRenderer.class.getResourceAsStream(resource)) {
            if (is != null) {
                byte[] ttf = is.readAllBytes();

                BaseFont bf = BaseFont.createFont(
                        resource,               // имя (можно любое)
                        BaseFont.IDENTITY_H,    // Unicode
                        BaseFont.EMBEDDED,      // встраивать
                        false,                  // cached
                        ttf,                    // байты TTF
                        null                    // AFM — null
                );
                log.info("Make a font '{}'", resource);
                return bf;
            }
            log.error("File '{}' not found", resource);
        } catch (Exception e) {
            log.error("Can't open font file '{}': {}", resource, e.getMessage());
        }
        // Возвращаем дефолтный фонт.
        return FontFactory.getFont(FontFactory.HELVETICA).getBaseFont();
    }


}
