package mr.demonid.config.categories;

import lombok.*;
import mr.demonid.config.validation.annotations.ColorCode;
import mr.demonid.config.validation.annotations.LineRange;
import mr.demonid.config.validation.annotations.ShapeStyle;

import java.awt.*;

/**
 * Кастомный стиль значков показаний для таблиц.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ShapeStyleCfg implements DefaultConfig<ShapeStyleCfg> {

    @ShapeStyle
    private String style;

    @LineRange
    private float size;

    @ColorCode
    private Color color;


    @Override
    public void applyDefault() {
        this.style = "CIRCLE";
        this.size = 2f;
        this.color = new Color(0x202020);
    }
}
