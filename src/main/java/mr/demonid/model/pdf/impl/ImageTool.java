package mr.demonid.model.pdf.impl;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import mr.demonid.chart.exporter.ChartExporter;
import mr.demonid.config.categories.MarginCfg;
import org.jfree.chart.JFreeChart;

import java.io.IOException;


public class ImageTool {

    private final MarginCfg margin;
    private final Rectangle page;
    private final ChartExporter exporter;


    public ImageTool(Rectangle page, MarginCfg margin, ChartExporter exporter) {
        this.exporter = exporter;
        this.margin = margin;
        this.page = page;
    }


    public Image createImage(JFreeChart chart, float width, float height, int dpi) throws IOException, BadElementException {

        float usableWidth = page.getWidth() - margin.getLeft() - margin.getRight();
        float usableHeight = page.getHeight() - margin.getTop() - margin.getBottom();
        float ptWidth = usableWidth / 100f * width;
        float ptHeight = usableHeight / 100f * height;

        Image img = Image.getInstance(exporter.toPNG(chart, ptWidth, ptHeight, dpi));
        img.scaleAbsolute(ptWidth , ptHeight);
        return img;
    }


    public void positionToTop(Image image, float incrX, float incrY) {
        image.setAbsolutePosition(margin.getLeft() + incrX, margin.getBottom() + incrY);
    }

    public void positionToBottom(Image image, float incrX, float incrY) {
        image.setAbsolutePosition(margin.getLeft() + incrX, page.getHeight() - margin.getTop() - (image.getScaledHeight() + incrY));
    }

}
