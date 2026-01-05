package mr.demonid.view.styles.shapestyle;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;


/**
 * Значок в виде точки с внешним кольцом.
 */
public class CircleStyle implements ShapeStyle {

    @Override
    public Shape createShape(float size) {
        float ringThickness = size / 15f;       // толщина линии кольца.
        float dotDiameter = size / 3f;          // размер центральной части

        double outerR = size / 2.0;
        double innerR = outerR - ringThickness;
        double dotR = dotDiameter / 2.0;

        // внешняя окружность
        Ellipse2D outer = new Ellipse2D.Double(-outerR, -outerR, size, size);
        // внутренняя окружность (будем вырезать)
        Ellipse2D inner = new Ellipse2D.Double(-innerR, -innerR, innerR * 2.0, innerR * 2.0);
        // центральная точка (заливка)
        Ellipse2D dot = new Ellipse2D.Double(-dotR, -dotR, dotDiameter, dotDiameter);

        // строим Area.
        Area ring = new Area(outer);
        ring.subtract(new Area(inner)); // вычитаем внутренний круг -> получаем кольцо
        // затем добавляем внутр. круг как UNION — получится ring + filled circle
        ring.add(new Area(dot));

        return ring;
    }

}
