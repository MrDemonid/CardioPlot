package mr.demonid.view.console;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;


/*
    TODO: Run->Edit->add VM options: -Djansi.passthrough=true
    чтобы в IDEA jansi не конфликтовала за вывод в цвете :)
 */

public class AnsiUtil {


    /**
     * Инициализация ANSI.
     * Также вешает хук на завершение программы, чтобы вызвать systemUninstall().
     */
    public static void initAnsiConsole() {
        AnsiConsole.systemInstall();
        // Регистрируем shutdown hook для завершения эмуляции ansi
        Runtime.getRuntime().addShutdownHook(new Thread(AnsiConsole::systemUninstall));
    }


    public static void print(String text, Ansi.Color fg) {
        System.out.println(Ansi.ansi().fg(fg).a(text).reset());
    }

    public static void print(String text, Ansi.Color fg, Ansi.Color bg) {
        System.out.println(Ansi.ansi().fg(fg).bg(bg).a(text).reset());
    }

    public static String colored(String text, Ansi.Color fg) {
        return Ansi.ansi().fg(fg).a(text).reset().toString();
    }

}
