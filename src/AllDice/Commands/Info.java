package AllDice.Commands;

import AllDice.Controllers.ClientController;
import AllDice.Helper.Helper;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class Info extends Command {
    public static String matchPattern = "^!info(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        String reply = "";
        reply += "Alldice - Infopage\n";
        reply += "Author: ~new (Jan Roth)";
        reply += "Current AllDice Version: " + clientController.sessionController.Version + "\n";
        reply += "AllDice Repository: https://github.com/NewPing/TeamSpeak-AllDice\n";
        reply += "AllDice Versions: https://github.com/NewPing/TeamSpeak-AllDice/releases\n";
        Helper.sendMessage(textEvent, clientController, reply, false);
    }
}