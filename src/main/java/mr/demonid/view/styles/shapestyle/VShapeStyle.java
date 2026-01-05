package mr.demonid.view.styles.shapestyle;

import java.awt.*;
import java.awt.geom.Path2D;


/**
 * Значок в виде 'V'.
 */
public class VShapeStyle implements ShapeStyle {

    @Override
    public Shape createShape(float size) {
        Path2D.Float p = new Path2D.Float();

        p.moveTo(-size/2, -size/4);
        p.lineTo(0, size/2);
        p.lineTo(size/2, -size/4);

        return p;
    }
}
