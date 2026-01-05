package mr.demonid.args.help;

import com.beust.jcommander.JCommander;


/**
 * Интерфейс предоставляющий метод вывода справки по параметрам командной строки.
 */
public interface IHelpParams {
    void showHelp(JCommander jCommander);
}
