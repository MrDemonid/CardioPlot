package mr.demonid.config.categories;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import mr.demonid.config.validation.annotations.FileName;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class App implements DefaultConfig<App> {

    @NotNull
    private String name;

    @FileName(mustExist = true)
    private String source;

    @FileName
    private String destination;


    @Override
    public void applyDefault() {
        this.name = "Pressure blood";
        this.source = "pressure.xlsx";
        this.destination = "report.pdf";
    }

}
