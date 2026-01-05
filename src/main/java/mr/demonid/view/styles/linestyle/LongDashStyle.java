package mr.demonid.view.styles.linestyle;

import java.awt.*;

public class LongDashStyle implements StrokeStyle {

    private static final float[] PATTERN = {5f, 3.5f};


    @Override
    public Stroke createStroke(float baseWidth) {
        return new BasicStroke(
                baseWidth,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                10f,
                PATTERN,
                0f
        );
    }

}
