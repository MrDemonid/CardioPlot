package mr.demonid.config.markersrule;

import lombok.Getter;
import lombok.ToString;
import mr.demonid.chart.builder.impl.MarkerRule;
import mr.demonid.view.styles.linestyle.StrokeManager;

import java.util.List;


/**
 * Стили маркерных линий граничных значений.
 */
@Getter
@ToString
public class MarkerLineCfg {

    public static final List<MarkerRule> pressureRules = List.of(
            new MarkerRule(60, StrokeManager.Type.CRITICAL),
            new MarkerRule(180, StrokeManager.Type.CRITICAL),
            new MarkerRule(80, StrokeManager.Type.SOLID),
            new MarkerRule(120, StrokeManager.Type.SOLID),
            new MarkerRule(90, StrokeManager.Type.LONG_DASH),
            new MarkerRule(140, StrokeManager.Type.LONG_DASH)
    );

    public static final List<MarkerRule> pulseRules = List.of(
            new MarkerRule(60, StrokeManager.Type.SOLID),
            new MarkerRule(80, StrokeManager.Type.SOLID),
            new MarkerRule(90, StrokeManager.Type.LONG_DASH)
    );

}
