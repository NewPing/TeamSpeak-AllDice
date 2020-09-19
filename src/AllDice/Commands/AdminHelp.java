package AllDice.Commands;

import AllDice.Controllers.Client;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class AdminHelp extends Command {
    public static String matchPattern = "^!adminhelp(:?((:?( )?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        Help.help(textEvent, client, true);
    }
}
