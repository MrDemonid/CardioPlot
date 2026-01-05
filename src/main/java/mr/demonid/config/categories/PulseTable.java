package mr.demonid.config.categories;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import mr.demonid.config.validation.annotations.PercentRange;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PulseTable implements DefaultConfig<PulseTable> {

    @PercentRange
    private float width;

    @PercentRange
    private float height;

    @NotNull
    @Valid
    private LineStyleCfg pulseLine;

    @NotNull
    @Valid
    private ShapeStyleCfg pulseMeasure;

    @NotNull
    @Valid
    private ShapeStyleCfg arrhythmia;


    @Override
    public void applyDefault() {
        this.width = 100f;
        this.height = 40f;
        this.pulseLine = DefaultConfig.create(LineStyleCfg.class);
        this.pulseMeasure = DefaultConfig.create(ShapeStyleCfg.class);
        this.arrhythmia = DefaultConfig.create(ShapeStyleCfg.class);
    }

}
