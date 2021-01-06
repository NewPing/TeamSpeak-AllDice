package AllDice.Commands.GameCommands;

import AllDice.Commands.GameCommands.ST;
import AllDice.Controllers.ClientController;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class STF extends Command {
    public static String matchPattern = "^!stf[0-9]+,[0-9]+,[0-9]+(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        ST.starTrek(textEvent, clientController, true);
    }
}