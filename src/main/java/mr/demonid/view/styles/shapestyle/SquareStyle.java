package mr.demonid.view.styles.shapestyle;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SquareStyle implements ShapeStyle {

    @Override
    public Shape createShape(float size) {
        float outerR = size / 2.0f;

        return new Rectangle2D.Float(-outerR, -outerR, size, size);
    }
}
