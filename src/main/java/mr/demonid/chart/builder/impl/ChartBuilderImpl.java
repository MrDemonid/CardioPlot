package mr.demonid.chart.builder.impl;

import lombok.AllArgsConstructor;
import mr.demonid.chart.builder.ChartBuilder;
import mr.demonid.chart.builder.DatasetService;
import mr.demonid.chart.services.ChartService;
import mr.demonid.chart.types.ChartLineStyle;
import mr.demonid.chart.types.GridLineStyle;
import mr.demonid.config.categories.LineStyleCfg;
import mr.demonid.config.categories.PressureTable;
import mr.demonid.config.categories.PulseTable;
import mr.demonid.config.categories.ViewConfig;
import mr.demonid.model.measurement.types.ChartBounds;
import mr.demonid.model.measurement.types.Measurement;
import mr.demonid.util.UnitConverter;
import mr.demonid.view.fonts.AwtFontProvider;
import mr.demonid.view.styles.linestyle.StrokeManager;
import org.jfree.chart.JFreeChart;
import org.jfree.data.Range;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class ChartBuilderImpl implements ChartBuilder {

    private final ChartService chartService;
    private final DatasetService datasetService;
    private final AwtFontProvider fontProvider;
    private final StrokeManager strokeManager;
    private final ViewConfig config;


    @Override
    public JFreeChart createPressureChart(List<Measurement> list, ChartBounds bounds, YearMonth ym) {
        TimeSeriesCollection pressureDataset = datasetService.buildPressureDataset(list);

        JFreeChart chart = chartService.createTimeSeriesChart(
                "Давление (верхнее/нижнее)",
                "День",
                "мм рт. ст.",
                pressureDataset
        );

        setupChart(bounds, chart, ym);
        setPressureChartLinesStyle(chart);

        return chart;
    }


    @Override
    public JFreeChart createPulseChart(List<Measurement> list, ChartBounds bounds, YearMonth ym) {
        TimeSeriesCollection pulseDataset = datasetService.buildPulseDataset(list);

        JFreeChart chart = chartService.createTimeSeriesChart(
                "Пульс",
                "День",
                "уд/мин",
                pulseDataset
        );

        setupChart(bounds, chart, ym);
        setPulseChartLinesStyle(chart);

        return chart;
    }


    /*
     * Основные настройки графика.
     * TODO: возможно лучше ввести темы для стиля графиков.
     */
    private void setupChart(ChartBounds bounds, JFreeChart chart, YearMonth ym) {
        LocalDate startOfMonth = ym.atDay(1);
        LocalDate endOfMonth = ym.atEndOfMonth();
        chartService.setChartBackground(chart, config.getColors().getBackground());
        setGridColors(chart);
        chartService.setRange(chart, new Range(UnitConverter.roundDown10(bounds.min()), UnitConverter.roundUp10(bounds.max())), 10);
        chartService.setDomain(chart, startOfMonth, endOfMonth);
        chartService.setFont(chart, fontProvider.getFont(config.getChart().getFontName(), config.getChart().getFontSize(), Font.PLAIN));
    }


    /*
     * Установка цветов и размеров линий сетки таблицы.
     */
    private void setGridColors(JFreeChart chart) {
        chartService.setGridLinesStyle(
                chart,
                new GridLineStyle(
                        config.getChart().getGridLineSize(),
                        config.getChart().getGridLineSize(),
                        config.getColors().getGridRange(),
                        config.getColors().getGridDomain()
                )
        );
    }

    /*
     * Установка стилей линий графика давления.
     */
    private void setPressureChartLinesStyle(JFreeChart chart) {
        PressureTable cfg = config.getPressureTable();
        LineStyleCfg sysLine = cfg.getSystolicLine();
        LineStyleCfg diaLine = cfg.getDiastolicLine();

        applyChartLinesStyle(chart, List.of(
                new ChartLineRawData(sysLine.getStyle(), sysLine.getSize(), sysLine.getColor()),
                new ChartLineRawData(diaLine.getStyle(), diaLine.getSize(), diaLine.getColor())
        ));
    }

    /*
     * Установка стилей линий графика пульса.
     */
    private void setPulseChartLinesStyle(JFreeChart chart) {
        PulseTable cfg = config.getPulseTable();
        LineStyleCfg line = cfg.getPulseLine();

        applyChartLinesStyle(chart, List.of(
                new ChartLineRawData(line.getStyle(), line.getSize(), line.getColor())
        ));
    }


    private void applyChartLinesStyle(JFreeChart chart, List<ChartLineRawData> styles) {
        List<ChartLineStyle> lineStyles = new ArrayList<>();

        for (ChartLineRawData style : styles) {
            if (style.size() > 0f) {
                lineStyles.add(new ChartLineStyle(
                        strokeManager.of(StrokeManager.Type.of(style.nameStyle()), style.size()),
                        style.color()));
            }
        }
        if (!lineStyles.isEmpty()) {
            chartService.setSeriesLinesStyle(chart, lineStyles);
        }
    }

}
