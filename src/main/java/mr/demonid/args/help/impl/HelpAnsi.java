package mr.demonid.args.help.impl;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;
import mr.demonid.cli.args.help.IHelpParams;
import mr.demonid.view.console.AnsiUtil;
import org.fusesource.jansi.Ansi;


/**
 * Справка с расцветкой ANSI.
 */
public class HelpAnsi implements IHelpParams {

    private static final String PARAM_SPACES = "    ";
    private static final String DESC_SPACES = "        ";

    private static final Ansi.Color COLOR_USAGE = Ansi.Color.GREEN;
    private static final Ansi.Color COLOR_OPTIONS = Ansi.Color.GREEN;
    private static final Ansi.Color COLOR_PARAM = Ansi.Color.CYAN;
    private static final Ansi.Color COLOR_DESCRIPTION = Ansi.Color.DEFAULT;


    @Override
    public void showHelp(JCommander jCommander) {
        System.out.println(generateHelp(jCommander));
    }


    private String generateHelp(JCommander jCommander) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(AnsiUtil.colored(String.format("\nUsage: %s [options]\n", jCommander.getProgramName()), COLOR_USAGE))
                .append(AnsiUtil.colored("Options:\n", COLOR_OPTIONS));

        for (var pd : jCommander.getParameters()) {
            appendParameter(stringBuilder, pd);
        }

        return stringBuilder.toString();
    }


    /**
     * Добавляет описание параметра в StringBuilder.
     */
    private void appendParameter(StringBuilder stringBuilder, ParameterDescription pd) {
        stringBuilder
                .append(PARAM_SPACES)
                .append(AnsiUtil.colored(String.join(", ", pd.getNames()), COLOR_PARAM))
                .append("\n");

        stringBuilder.append(AnsiUtil.colored(parseDescription(pd.getDescription()), COLOR_DESCRIPTION));
    }


    private String parseDescription(String desc) {
        StringBuilder sb = new StringBuilder();
        String[] split = desc.split("\n");
        for (String s : split) {
            sb.append(DESC_SPACES).append(s).append("\n");
        }
        return sb.toString();
    }

}
