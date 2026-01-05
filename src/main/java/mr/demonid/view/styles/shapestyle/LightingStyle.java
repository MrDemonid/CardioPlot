package mr.demonid.view.styles.shapestyle;

import java.awt.*;
import java.awt.geom.Path2D;


/**
 * Значок в виде молнии.
 */
public class LightingStyle implements ShapeStyle {

    @Override
    public Shape createShape(float size) {
        Path2D.Float p = new Path2D.Float();

        p.moveTo(0, -size * 0.5);
        p.lineTo(size * 0.2, -size * 0.1);
        p.lineTo(-size * 0.1, -size * 0.1);
        p.lineTo(size * 0.1, size * 0.5);
        p.lineTo(size * -0.2, size * 0.1);
        p.lineTo(0, size * 0.1);
        p.closePath();
        return p;
    }

}
