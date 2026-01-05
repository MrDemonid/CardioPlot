package mr.demonid.args;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import mr.demonid.cli.args.help.IHelpParams;

import java.util.Optional;


/**
 * Разбор параметров командной строки.
 */
public class ParamBuilder {

    private static final String DEFAULT_CONFIG_FILE = "default.yml";

    private final JCommander jCommander;
    private final IHelpParams helpParams;


    public ParamBuilder(String programName, IHelpParams helpParams) {
        AppParams params = new AppParams();
        jCommander = JCommander
                .newBuilder()
                .addObject(params)
                .acceptUnknownOptions(false)
                .allowParameterOverwriting(false)
                .columnSize(40)
                .build();
        jCommander.setProgramName(programName);

        this.helpParams = helpParams;
    }

    /**
     * Парсит аргументы командной строки и возвращает параметры приложения.
     * Всегда возвращает корректно проинициализированные поля AppParams, если они имеют смысл.
     * Не проверяет существование файлов и корректность их имён, это не его ответственность.
     *
     * @param args Аргументы командной строки из main().
     * @return Объект с параметрами или пустой Optional, если запрошена справка.
     * @throws ParameterException При некорректных аргументах.
     */
    public Optional<AppParams> parse(String[] args) throws ParameterException {

        jCommander.parse(args);
        Object obj = jCommander.getObjects().getFirst();
        if (obj instanceof AppParams params) {
            if (params.isHelp()) {
                return Optional.empty();
            }

            if (params.getConfigFile() == null || params.getConfigFile().isBlank()) {
                params.setConfigFile(DEFAULT_CONFIG_FILE);
            }
            return Optional.of(params);
        }
        return Optional.empty();
    }


    /**
     * Вывод справки по параметрам командной строки.
     */
    public void showHelp() {
        helpParams.showHelp(jCommander);
    }

}
