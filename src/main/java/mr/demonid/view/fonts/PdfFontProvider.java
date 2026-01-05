package mr.demonid.view.fonts;


public interface PdfFontProvider {
    com.itextpdf.text.Font getFont(String resource, float size, int style);
}
