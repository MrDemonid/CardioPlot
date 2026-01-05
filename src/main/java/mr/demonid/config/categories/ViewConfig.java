package mr.demonid.config.categories;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ViewConfig implements DefaultConfig<ViewConfig> {
    @NotNull
    @Valid
    private TitleCfg title;

    @NotNull
    @Valid
    private MarginCfg margin;

    @NotNull
    @Valid
    private ChartCfg chart;

    @NotNull
    @Valid
    private ColorsCfg colors;

    @NotNull
    @Valid
    private PressureTable pressureTable;

    @NotNull
    @Valid
    private PulseTable pulseTable;


    @Override
    public void applyDefault() {
        title = DefaultConfig.create(TitleCfg.class);
        margin = DefaultConfig.create(MarginCfg.class);
        chart = DefaultConfig.create(ChartCfg.class);
        colors = DefaultConfig.create(ColorsCfg.class);
        pressureTable = DefaultConfig.create(PressureTable.class);
        pulseTable = DefaultConfig.create(PulseTable.class);
    }

}