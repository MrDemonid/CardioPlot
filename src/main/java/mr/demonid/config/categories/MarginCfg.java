package mr.demonid.config.categories;

import lombok.*;
import mr.demonid.config.validation.annotations.MarginRange;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class MarginCfg implements DefaultConfig<MarginCfg> {
    @MarginRange
    private Integer left;

    @MarginRange
    private Integer top;

    @MarginRange
    private Integer right;

    @MarginRange
    private Integer bottom;


    @Override
    public void applyDefault() {
        this.left = 20;
        this.top = 20;
        this.right = 20;
        this.bottom = 20;
    }

}