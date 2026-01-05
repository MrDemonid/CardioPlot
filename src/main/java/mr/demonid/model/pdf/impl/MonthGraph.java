package mr.demonid.model.pdf.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import mr.demonid.chart.builder.ChartBuilder;
import mr.demonid.chart.builder.MarkerBuilder;
import mr.demonid.config.categories.ViewConfig;
import mr.demonid.model.measurement.types.ChartBounds;
import mr.demonid.model.measurement.types.Measurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.JFreeChart;

import java.time.YearMonth;
import java.util.List;


public class MonthGraph {

    private static final Logger log = LogManager.getLogger(MonthGraph.class.getName());

    private final TitleRenderer titleRenderer;
    private final ChartBuilder chartBuilder;
    private final MarkerBuilder markerBuilder;
    private final ImageTool imageTool;
    private final ViewConfig config;


    public MonthGraph(TitleRenderer titleRenderer,
                      ChartBuilder chartBuilder,
                      MarkerBuilder markerBuilder,
                      ImageTool imageTool,
                      ViewConfig config) {
        this.titleRenderer = titleRenderer;
        this.chartBuilder = chartBuilder;
        this.markerBuilder = markerBuilder;
        this.imageTool = imageTool;
        this.config = config;
    }


    public void addMonthPage(Document pdf, PdfWriter pdfWriter, YearMonth ym, List<Measurement> list, ChartBounds boundsPress, ChartBounds boundsPulse) throws Exception {
        int dpi = config.getChart().getDpi();

        // создаем графики
        JFreeChart chartPress = chartBuilder.createPressureChart(list, boundsPress, ym);
        JFreeChart chartPulse = chartBuilder.createPulseChart(list, boundsPulse, ym);

        // добавляем маркеры на графики
        markerBuilder.setPressureMarkers(chartPress);
        markerBuilder.setPulseMarkers(chartPulse);

        // Новая страницы в PDF
        pdf.newPage();

        // Добавляем заголовок
        float titleHeight = titleRenderer.renderTitle(pdf, pdfWriter, ym);
        titleHeight += config.getTitle().getMarginBottom();

        // рассчитываем размеры страниц и областей под графики
        Image imagePress = imageTool.createImage(chartPress, config.getPressureTable().getWidth(), config.getPressureTable().getHeight(), dpi);
        imageTool.positionToBottom(imagePress, 0f, titleHeight);

        Image imagePulse = imageTool.createImage(chartPulse, config.getPulseTable().getWidth(), config.getPulseTable().getHeight(), dpi);
        imageTool.positionToTop(imagePulse, 0f, 0f);

        pdf.add(imagePress);
        pdf.add(imagePulse);
    }

}
