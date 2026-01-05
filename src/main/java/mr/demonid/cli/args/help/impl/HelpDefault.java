package mr.demonid.cli.args.help.impl;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;
import mr.demonid.cli.args.help.IHelpParams;


/**
 * Справка по параметрам командной строки.
 */
public class HelpDefault implements IHelpParams {

    private static final String PARAM_SPACES = "    ";
    private static final String DESC_SPACES = "        ";


    @Override
    public void showHelp(JCommander jCommander) {
        System.out.println(generateHelp(jCommander));
    }


    private String generateHelp(JCommander jCommander) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(String.format("Usage: %s [options]\n", jCommander.getProgramName()))
                .append("Options:\n");

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
                .append(String.join(", ", pd.getNames()))
                .append("\n");

        stringBuilder.append(parseDescription(pd.getDescription()));
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
