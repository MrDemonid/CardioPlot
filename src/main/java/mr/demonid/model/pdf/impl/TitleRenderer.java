package mr.demonid.model.pdf.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import mr.demonid.config.categories.TitleCfg;
import mr.demonid.config.categories.ViewConfig;
import mr.demonid.view.fonts.PdfFontProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class TitleRenderer {

    private static final Logger log = LogManager.getLogger(TitleRenderer.class.getName());

    private final PdfFontProvider fontProvider;
    private final TitleCfg config;


    public TitleRenderer(PdfFontProvider fontProvider, ViewConfig viewConfig) {
        this.fontProvider = fontProvider;
        this.config = viewConfig.getTitle();
    }


    /**
     * Отрисовка заголовка в документ.
     * @param pdf Документ.
     * @param ym  Год/месяц.
     * @return Высота заголовка в pt.
     */
    public float renderTitle(Document pdf, PdfWriter writer, YearMonth ym) throws DocumentException {
        try {
            Paragraph p = new Paragraph(buildMonthTitle(ym), getTitleFont());
            p.setAlignment(Element.ALIGN_CENTER);
            pdf.add(p);
            return getHeight(p, writer);
        } catch (Exception e) {
            log.error("Can't render title: {}", e.getMessage());
            throw e;
        }
    }


    /**
     * Возвращает высоту параграфа.
     * @param paragraph Параграф.
     * @return Высота параграфа в pt.
     */
    private int getHeight(Paragraph paragraph, PdfWriter writer) throws DocumentException {
        PdfContentByte cb = writer.getDirectContent();
        ColumnText ct = new ColumnText(cb);
        ct.setSimpleColumn(0, 0, 1200, 600);    // чтобы текст разместился
        ct.addElement(paragraph);
        ct.go(true);                            // true = simulate, не выводит
        return (int) (600 - ct.getYLine());
    }

    /**
     * Создание заголовка страницы.
     * @param ym Год/месяц.
     * @return Строку заголовка, либо на русском, либо на енглиш.
     */
    private String buildMonthTitle(YearMonth ym) {
        Font font = getTitleFont();
        DateTimeFormatter fmt = isSupportCyrillic(font) ?
                DateTimeFormatter.ofPattern("LLLL yyyy", Locale.forLanguageTag("ru")) :
                DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        String mn = ym.format(fmt);
        return Character.toUpperCase(mn.charAt(0)) + mn.substring(1);
    }


    private Font getTitleFont() {
        return fontProvider.getFont(config.getFontName(), config.getFontSize(), Font.NORMAL);
    }

    private boolean isSupportCyrillic(Font font) {
        return font != null && font.getBaseFont().getWidth("Ф") > 0;
    }

}
