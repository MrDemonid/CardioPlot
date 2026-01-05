package mr.demonid.view.styles.shapestyle;


import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Значок в виде кардиограммы (пик ЭКГ)
 */
public class ECGStyle implements ShapeStyle {

    @Override
    public Shape createShape(float size) {
        Path2D.Float p = new Path2D.Float();

        float h = size * 0.5f;

        p.moveTo(-size / 2, 0);
        p.lineTo(-size / 4, 0);
        p.lineTo(-size / 8, -h);
        p.lineTo(0, h);
        p.lineTo(size / 8, -h);
        p.lineTo(size / 4, 0);
        p.lineTo(size / 2, 0);

        return p;
    }
}
