package mr.demonid.view.styles.shapestyle;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class RectStyle implements ShapeStyle {

    @Override
    public Shape createShape(float size) {
        float radius = size / 2f;

        float ringThickness = size / 10f;       // толщина линии.

        float innerR = radius - ringThickness;

        // внешний квадрат
        Rectangle2D.Float rectangle = new Rectangle2D.Float(-radius, -radius, size, size);
        // внутренний квадрат (вырежем заливку по нему)
        Rectangle2D.Float inner = new Rectangle2D.Float(-innerR, -innerR, innerR*2f, innerR*2f);

        // строим Area.
        Area rect = new Area(rectangle);
        rect.subtract(new Area(inner));

        return rect;
    }
}
