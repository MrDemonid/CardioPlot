package mr.demonid;

import com.beust.jcommander.ParameterException;
import mr.demonid.chart.builder.ChartBuilder;
import mr.demonid.chart.exporter.ChartExporter;
import mr.demonid.chart.builder.MarkerBuilder;
import mr.demonid.chart.builder.impl.ChartBuilderImpl;
import mr.demonid.chart.exporter.impl.ChartExporterImpl;
import mr.demonid.chart.builder.impl.MarkerBuilderImpl;
import mr.demonid.chart.services.ChartService;
import mr.demonid.chart.services.MarkerService;
import mr.demonid.chart.services.ChartScaleService;
import mr.demonid.view.styles.shapestyle.ShapesManager;
import mr.demonid.view.styles.linestyle.StrokeManager;
import mr.demonid.chart.builder.impl.DatasetServiceImpl;
import mr.demonid.view.fonts.impl.AwtFontProviderImpl;
import mr.demonid.config.Config;
import mr.demonid.config.categories.ViewConfig;
import mr.demonid.cli.args.AppParams;
import mr.demonid.cli.args.ParamBuilder;
import mr.demonid.cli.args.help.impl.HelpAnsi;
import mr.demonid.model.pdf.impl.PdfGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fusesource.jansi.Ansi;

import java.util.Optional;

import static mr.demonid.view.console.AnsiUtil.initAnsiConsole;
import static mr.demonid.view.console.AnsiUtil.print;


public class CardioPlot {

    private final Logger log = LogManager.getLogger(getClass().getName());


    public static void main(String[] args) {
        CardioPlot app = new CardioPlot();
        app.runApp(args);
    }


    void runApp(String[] args) {
        initAnsiConsole();

        print(getClass().getSimpleName() + " started", Ansi.Color.CYAN);
        Config config = loadConfiguration(args);

        // генерируем pdf
        PdfGenerator generator = getPdfGenerator(new StrokeManager(), config.getViewConfig(), new ShapesManager());
        generator.generate(config.getApp().getSource(), config.getApp().getDestination());

        print(getClass().getSimpleName() + " finished", Ansi.Color.CYAN);
    }


    private Config loadConfiguration(String[] args) {
        int exitCode = 1;
        ParamBuilder paramBuilder = new ParamBuilder(getClass().getSimpleName(), new HelpAnsi());
        try {
            Optional<AppParams> params = paramBuilder.parse(args);
            if (params.isPresent()) {
                return new Config(params.get().getConfigFile());
            }
            exitCode = 0;
        } catch (ParameterException e) {
            log.error("Invalid parameters: {}", e.getMessage());
        }
        paramBuilder.showHelp();
        System.exit(exitCode);
        return null;
    }


    private PdfGenerator getPdfGenerator(StrokeManager strokeManager, ViewConfig viewConfig, ShapesManager shapesManager) {
        ChartBuilder chartBuilder = new ChartBuilderImpl(
                new ChartService(),
                new DatasetServiceImpl(),
                new AwtFontProviderImpl(),
                strokeManager,
                viewConfig);
        MarkerBuilder markerBuilder = new MarkerBuilderImpl(new MarkerService(),
                shapesManager,
                strokeManager,
                viewConfig);
        ChartExporter chartExporter = new ChartExporterImpl(new ChartScaleService());

        return new PdfGenerator(chartBuilder, markerBuilder, chartExporter, viewConfig);
    }

}
