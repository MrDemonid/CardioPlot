package mr.demonid.view.styles.shapestyle;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class DotStyle implements ShapeStyle {

    @Override
    public Shape createShape(float size) {
        double outerR = size / 2.0;

        // внешняя окружность
        return new Ellipse2D.Double(-outerR, -outerR, size, size);
    }

}
