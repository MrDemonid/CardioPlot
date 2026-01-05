package mr.demonid.config.categories;

import lombok.*;
import mr.demonid.config.validation.annotations.ColorCode;

import java.awt.*;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ColorsCfg implements DefaultConfig<ColorsCfg> {
    @ColorCode
    private Color background;

    @ColorCode
    private Color text;

    @ColorCode
    private Color gridRange;

    @ColorCode
    private Color gridDomain;

    @ColorCode
    private Color markerLine;


    @Override
    public void applyDefault() {
        this.background = new Color(0xFFFFFF);
        this.text       = new Color(0x000000);
        this.gridRange  = new Color(0x646464);
        this.gridDomain = new Color(0x909090);
        this.markerLine = new Color(0x606060);
    }

}