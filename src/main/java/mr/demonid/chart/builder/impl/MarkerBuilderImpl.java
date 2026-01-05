package mr.demonid.chart.builder.impl;

import mr.demonid.chart.builder.MarkerBuilder;
import mr.demonid.chart.services.MarkerService;
import mr.demonid.chart.types.MarkerStyle;
import mr.demonid.config.categories.ShapeStyleCfg;
import mr.demonid.config.categories.ViewConfig;
import mr.demonid.config.markersrule.MarkerLineCfg;
import mr.demonid.view.styles.linestyle.StrokeManager;
import mr.demonid.view.styles.shapestyle.ShapesManager;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.Layer;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;


public class MarkerBuilderImpl implements MarkerBuilder {

    private final MarkerService service;
    private final ViewConfig config;
    private final StrokeManager strokeManager;
    private final ShapesManager shapesManager;


    public MarkerBuilderImpl(MarkerService service, ShapesManager shapesManager, StrokeManager strokeManager, ViewConfig config) {
        this.service = service;
        this.config = config;
        this.strokeManager = strokeManager;
        this.shapesManager = shapesManager;
    }

    @Override
    public void setPressureMarkers(JFreeChart chart) {
        // маркеры систолического и диастолического давления
        applyMarker(chart, 2, config.getPressureTable().getSystolicMeasure());
        applyMarker(chart, 3, config.getPressureTable().getDiastolicMeasure());
        // линии граничных значений давления
        service.setMarkerStrike(chart, rulesToStyles(MarkerLineCfg.pressureRules, config));
    }

    @Override
    public void setPulseMarkers(JFreeChart chart) {
        // маркеры аритмии и пульса
        applyMarker(chart, 1, config.getPulseTable().getPulseMeasure());
        applyMarker(chart, 2, config.getPulseTable().getArrhythmia());
        // линии граничных значений пульса
        service.setMarkerStrike(chart, rulesToStyles(MarkerLineCfg.pulseRules, config));
    }


    private List<MarkerStyle> rulesToStyles(List<MarkerRule> rules, ViewConfig config) {
        Color bg = config.getColors().getBackground();
        Color fg = config.getColors().getMarkerLine();
        float size = config.getChart().getMarkerLineSize();

        return rules.stream()
                .flatMap(e -> Stream.of(
                        new MarkerStyle(e.value(), strokeManager.of(StrokeManager.Type.SOLID, size), bg, Layer.BACKGROUND),
                        new MarkerStyle(e.value(), strokeManager.of(e.type(), size), fg, Layer.FOREGROUND)
                ))
                .toList();
    }

    private void applyMarker(JFreeChart chart, int layer, ShapeStyleCfg shape) {
        service.setMarkerShape(
                chart,
                layer,
                shapesManager.of(ShapesManager.Type.of(shape.getStyle()), shape.getSize()),
                shape.getColor()
        );
    }

}
