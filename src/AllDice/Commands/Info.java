package AllDice.Commands;

import AllDice.Controllers.Client;
import AllDice.Helper.Helper;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class Info extends Command {
    public static String matchPattern = "^!info(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.matches(matchPattern);
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        String reply = "";
        reply += "Alldice - Infopage\n";
        reply += "Current AllDice Version: " + client.controller.Version + "\n";
        reply += "AllDice Repository: https://github.com/NewPing/TeamSpeak-AllDice\n";
        reply += "AllDice Versions: https://github.com/NewPing/TeamSpeak-AllDice/releases\n";
        Helper.sendMessage(textEvent, client, reply, false);
    }
}