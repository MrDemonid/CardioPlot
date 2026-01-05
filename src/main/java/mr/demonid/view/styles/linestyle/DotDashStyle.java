package mr.demonid.view.styles.linestyle;

import java.awt.*;

public class DotDashStyle implements StrokeStyle {

    private static final float[] PATTERN = {1f, 3f, 5f, 3f};


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
