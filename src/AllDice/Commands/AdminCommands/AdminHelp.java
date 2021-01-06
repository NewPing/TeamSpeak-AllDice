package AllDice.Commands.AdminCommands;

import AllDice.Commands.BasicCommands.Help;
import AllDice.Controllers.ClientController;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class AdminHelp extends Command {
    public static String matchPattern = "^!adminhelp(:?((:?( )?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        Help.help(textEvent, clientController, true);
    }
}
