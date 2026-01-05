package mr.demonid.view.styles.shapestyle;

import java.awt.*;


/**
 * Фабрика стилей значков аритмии.
 */
public class ShapesManager {

    /**
     * Типы доступных стилей.
     */
    public enum Type {
        LIGHTING(new LightingStyle()),
        ECG(new ECGStyle()),
        VSHAPE(new VShapeStyle()),
        CIRCLE(new CircleStyle()),
        DOT(new DotStyle()),
        SQUARE(new SquareStyle()),
        RECT(new RectStyle());

        private final ShapeStyle impl;

        Type(ShapeStyle impl) {
            this.impl = impl;
        }

        public static Type of(String name) {
            try {
                return valueOf(name);
            } catch (IllegalArgumentException e) {
                return CIRCLE;
            }
        }

    }


    /**
     * Создание новой фигуры.
     * @param shape Тип фигуры.
     * @param size  Её размер.
     */
    public Shape of(Type shape, float size) {
        return shape.impl.createShape(size);
    }

}
