package mr.demonid.view.styles.linestyle;

import java.awt.*;


/**
 * Сплошная линия.
 */
public class SolidStyle implements StrokeStyle {
    @Override
    public Stroke createStroke(float baseWidth) {
        return new BasicStroke(baseWidth);
    }
}
