package mr.demonid.view.styles.linestyle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;


@Getter
@Setter
@AllArgsConstructor
public final class StrokeManager {

    /**
     * Типы маркерных линий.
     */
    public enum Type {
        SOLID(new SolidStyle()),
        DOTTED(new DottedStyle()),
        DASH(new DashStyle()),
        SHORT_DASH(new ShortDashStyle()),
        LONG_DASH(new LongDashStyle()),
        DOT_DASH(new DotDashStyle()),
        CRITICAL(new CriticalStyle());

        private final StrokeStyle impl;

        Type(StrokeStyle impl) {
            this.impl = impl;
        }

        public static Type of(String name) {
            try {
                return valueOf(name);
            } catch (IllegalArgumentException e) {
                return SOLID;
            }
        }
    }


    /**
     * Создание новой линии.
     * @param type Тип линии.
     * @param size Её размер.
     */
    public Stroke of(Type type, float size) {
        return type.impl.createStroke(size);
    }

}
