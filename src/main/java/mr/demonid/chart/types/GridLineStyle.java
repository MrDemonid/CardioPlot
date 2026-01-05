package mr.demonid.chart.types;

import java.awt.*;

public record GridLineStyle(
    float rangeSize,
    float domainSize,
    Color rangeColor,
    Color domainColor) {
}
