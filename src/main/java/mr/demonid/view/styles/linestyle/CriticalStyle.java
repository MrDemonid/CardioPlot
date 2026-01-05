package mr.demonid.view.styles.linestyle;

import java.awt.*;


/**
 * Длинные штрихи.
 */
public class CriticalStyle implements StrokeStyle {

    private static final float[] PATTERN = {
            7f, 3f,   // длинный штрих + пробел
            2f,  3f    // точка (короткий штрих) + пробел
    };


    @Override
    public Stroke createStroke(float baseWidth) {
        return new BasicStroke(
                baseWidth * 1.2f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                10f,
                PATTERN,
                0f
        );
    }
}