package mr.demonid.model.pdf.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import mr.demonid.chart.builder.ChartBuilder;
import mr.demonid.chart.builder.MarkerBuilder;
import mr.demonid.chart.exporter.ChartExporter;
import mr.demonid.config.categories.MarginCfg;
import mr.demonid.config.categories.ViewConfig;
import mr.demonid.model.loader.impl.XLSReader;
import mr.demonid.model.measurement.MeasurementManager;
import mr.demonid.model.pdf.ReportGenerator;
import mr.demonid.view.fonts.impl.PdfFontProviderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;


public class PdfGenerator implements ReportGenerator {

    private final Logger log = LogManager.getLogger(getClass().getName());
    private final MonthGraph monthGraph;
    private final MarginCfg margin;


    public PdfGenerator(ChartBuilder chartBuilder, MarkerBuilder markerBuilder, ChartExporter chartExporter, ViewConfig config) {
        this.margin = config.getMargin();
        this.monthGraph = new MonthGraph(
                new TitleRenderer(new PdfFontProviderImpl(), config),
                chartBuilder,
                markerBuilder,
                new ImageTool(PageSize.A4.rotate(),config.getMargin(), chartExporter),
                config);
    }


    @Override
    public void generate(String excelPath, String outFile) {
        log.info("Creating PDF from: '{}', to: '{}'", excelPath, outFile);

        Document pdf = new Document(PageSize.A4.rotate(), margin.getLeft(), margin.getRight(), margin.getTop(), margin.getBottom());

        try {
            MeasurementManager measures = new MeasurementManager(new XLSReader(excelPath));
            PdfWriter pdfWriter = PdfWriter.getInstance(pdf, new FileOutputStream(outFile));
            pdf.open();
            // создаем графики для каждого месяца
            for (var month: measures) {
                monthGraph.addMonthPage(pdf, pdfWriter, month.month(), month.measurements(), measures.getBoundsPress(), measures.getBoundsPulse());
            }

        } catch (Exception e) {
            log.error("Error creating pdf: {}", e.getMessage(), e);
        } finally {
            try {
                pdf.close();
            } catch (Exception e) {
                log.error("Error closing pdf: {}", e.getMessage());
            }
        }

    }
}
