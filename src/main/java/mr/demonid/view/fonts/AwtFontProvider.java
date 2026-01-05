package mr.demonid.view.fonts;


public interface AwtFontProvider {
    java.awt.Font getFont(String resource, float size, int style);
}
