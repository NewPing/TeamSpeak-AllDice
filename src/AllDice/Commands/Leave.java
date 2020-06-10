package AllDice.Commands;

import AllDice.Client;
import AllDice.Helper.Helper;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class Leave extends Command {
    public static String matchPattern = "^!leave(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.matches(matchPattern);
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        if (textEvent.getInvokerId() == client.followClientID){
            Helper.sendMessage(textEvent, client, "leaving...", false ,false);
            client.controller.clientLeave(client.clientID);
            client.query.exit();
        }
    }
}