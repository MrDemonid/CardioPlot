package mr.demonid.config.categories;

import lombok.*;
import mr.demonid.config.validation.annotations.ColorCode;
import mr.demonid.config.validation.annotations.LineRange;
import mr.demonid.config.validation.annotations.LineStyle;

import java.awt.*;


/**
 * Кастомный стиль линий для таблиц.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class LineStyleCfg implements DefaultConfig<LineStyleCfg> {
    @LineStyle
    private String style;

    @LineRange
    private float size;

    @ColorCode
    private Color color;


    @Override
    public void applyDefault() {
        style = "SOLID";
        size = 0.8f;
        color = new Color(0x101010);
    }
}
