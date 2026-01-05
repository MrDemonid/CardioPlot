package mr.demonid.chart.types;

import org.jfree.chart.ui.Layer;

import java.awt.*;


/**
 * Стиль маркерной линии.
 * @param value
 * @param stroke
 * @param color
 */
public record MarkerStyle(int value, Stroke stroke, Color color, Layer layer) {
}
