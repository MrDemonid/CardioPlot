package mr.demonid.config.categories;

import lombok.*;
import mr.demonid.config.validation.annotations.DpiRange;
import mr.demonid.config.validation.annotations.FileName;
import mr.demonid.config.validation.annotations.FontRange;
import mr.demonid.config.validation.annotations.LineRange;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ChartCfg implements DefaultConfig<ChartCfg> {

    @FileName
    private String fontName;

    @FontRange
    private float fontSize;

    @DpiRange
    private int dpi;

    @LineRange
    private float gridLineSize;

    @LineRange
    private float markerLineSize;


    @Override
    public void applyDefault() {
        this.fontName = "/fonts/arial.ttf";
        this.fontSize = 10.0f;
        this.dpi = 72;
        this.gridLineSize = 0.2f;
        this.markerLineSize = 0.8f;
    }

}
