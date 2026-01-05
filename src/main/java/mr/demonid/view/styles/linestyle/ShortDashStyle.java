package mr.demonid.view.styles.linestyle;

import java.awt.*;

public class ShortDashStyle implements StrokeStyle {

    private static final float[] PATTERN = {2f, 2f};


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
