package AllDice.Commands;

import AllDice.Controllers.ClientController;
import AllDice.Helper.Helper;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class PM extends Command {
    public static String matchPattern = "^!pm(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        Helper.sendMessage(textEvent, clientController, "Opened private chat...", true);
    }
}