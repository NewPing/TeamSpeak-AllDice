package AllDice.Commands.AdminCommands;

import AllDice.Commands.BasicCommands.Leave;
import AllDice.Controllers.ClientController;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class ForceLeave extends Command {
    public static String matchPattern = "^!forceLeave(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        Leave.leave(textEvent, clientController, "Admin forced leave: leaving...");
    }
}