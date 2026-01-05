package mr.demonid.view.styles.linestyle;

import java.awt.*;


/**
 * Точечный.
 */
public class DottedStyle implements StrokeStyle {

    private static final float[] PATTERN = {1.2f, 1.2f};


    @Override
    public Stroke createStroke(float baseWidth) {
        return new BasicStroke(
                baseWidth,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                10f,
                PATTERN,
                0f
        );
    }
}
