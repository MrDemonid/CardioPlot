package mr.demonid.config.categories;

import lombok.*;
import mr.demonid.config.validation.annotations.FileName;
import mr.demonid.config.validation.annotations.FontRange;
import mr.demonid.config.validation.annotations.MarginRange;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TitleCfg implements DefaultConfig<TitleCfg> {

    @FileName
    private String fontName;

    @FontRange
    private float fontSize;

    @MarginRange
    private Integer marginBottom;


    @Override
    public void applyDefault() {
        this.fontName = "/fonts/arial.ttf";
        this.fontSize = 16f;
        this.marginBottom = 4;
    }

}
