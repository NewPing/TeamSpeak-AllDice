package AllDice.Commands;

import AllDice.Controllers.Client;
import AllDice.Helper.Helper;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class Leave extends Command {
    public static String matchPattern = "^!leave(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        if (textEvent.getInvokerId() == client.followClientID){
            Helper.sendMessage(textEvent, client, "leaving...", false);
            client.controller.clientLeave(client.clientID);
            client.query.exit();
        } else {
            Helper.sendMessage(textEvent, client, "Cant force me to leave, when you are not the one i m following...", false);
        }
    }
}