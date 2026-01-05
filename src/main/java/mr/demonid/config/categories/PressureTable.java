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
public class PressureTable implements DefaultConfig<PressureTable> {

    @PercentRange
    private float width;

    @PercentRange
    private float height;

    @NotNull
    @Valid
    private LineStyleCfg systolicLine;

    @NotNull
    @Valid
    private LineStyleCfg diastolicLine;

    @NotNull
    @Valid
    private ShapeStyleCfg systolicMeasure;

    @NotNull
    @Valid
    private ShapeStyleCfg diastolicMeasure;


    @Override
    public void applyDefault() {
        this.width = 100f;
        this.height = 52f;
        this.systolicLine = DefaultConfig.create(LineStyleCfg.class);
        this.diastolicLine = DefaultConfig.create(LineStyleCfg.class);
        this.systolicMeasure = DefaultConfig.create(ShapeStyleCfg.class);
        this.diastolicMeasure = DefaultConfig.create(ShapeStyleCfg.class);
    }
}
