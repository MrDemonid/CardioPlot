package mr.demonid.args;

import com.beust.jcommander.Parameter;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppParams {
    @Parameter(
            names = {"-c", "--config-file"},
            description = """
                    Include configuration file.
                      Available presets:
                        - default.yml: for black/white printing.
                        - screen.yml: for color printing.
                    Using default.yml if not specified.
                    """
    )
    private String configFile;

    @Parameter(names = {"-h", "--help"}, help = true, description = "This help")
    private boolean help;
}
