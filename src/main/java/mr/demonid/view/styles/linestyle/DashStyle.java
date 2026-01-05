package mr.demonid.view.styles.linestyle;

import java.awt.*;


/**
 * Пунктир.
 */
public class DashStyle implements StrokeStyle {

    private static final float[] PATTERN = {4f, 4f};


    @Override
    public Stroke createStroke(float baseWidth) {
        return new BasicStroke(
                baseWidth,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10f,
                PATTERN,
                0f
        );
    }
}
