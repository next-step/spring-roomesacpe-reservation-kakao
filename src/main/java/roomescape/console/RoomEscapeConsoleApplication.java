package roomescape.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import roomescape.console.view.InputView;
import roomescape.controller.ConsoleController;

@Profile("!test")
@Component
public class RoomEscapeConsoleApplication implements CommandLineRunner {

    private static final String RESERVATION_ADD = "res_add";
    private static final String RESERVATION_FIND = "res_find";
    private static final String RESERVATION_DELETE = "res_delete";
    private static final String THEME_ADD = "the_add";
    private static final String THEME_FIND = "the_find";
    private static final String THEME_LIST = "the_list";
    private static final String THEME_DELETE = "the_delete";
    private static final String QUIT = "quit";

    public static void main(String[] args) {
        boolean repeat = true;
        while (repeat) {
            String input = InputView.getCommand();
            executeCommand(input);
            repeat = isRepeat(input);
        }
    }

    public static void executeCommand(String input) {
        if (input.startsWith(RESERVATION_ADD)) {
            ConsoleController.createReservation(input);
        }

        if (input.startsWith(RESERVATION_FIND)) {
            ConsoleController.findReservation(input);
        }

        if (input.startsWith(RESERVATION_DELETE)) {
            ConsoleController.removeReservation(input);
        }

        if(input.startsWith(THEME_ADD)) {
            ConsoleController.createTheme(input);
        }

        if(input.startsWith(THEME_FIND)) {
            ConsoleController.findTheme(input);
        }

        if(input.startsWith(THEME_LIST)) {
            ConsoleController.listTheme();
        }

        if(input.startsWith(THEME_DELETE)) {
            ConsoleController.removeTheme(input);
        }
    }

    public static boolean isRepeat(String input) {
        return input.equals(QUIT);
    }

    @Override
    public void run(String... args) {
        main(args);
    }
}
