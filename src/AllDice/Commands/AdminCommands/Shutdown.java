package AllDice.Commands.AdminCommands;

import AllDice.Controllers.ClientController;
import AllDice.Helper.Helper;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class Shutdown extends Command {
    public static String matchPattern = "^!shutdown(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        clientController.sessionController.shutdown();
    }
}