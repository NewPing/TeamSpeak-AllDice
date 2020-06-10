package AllDice.Commands;

import AllDice.Client;
import AllDice.Helper.Helper;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class Follow extends Command {
    public static String matchPattern = "^!follow(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.matches(matchPattern);
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        if (client.followClientID == -1){
            client.followClientID = textEvent.getInvokerId();
            Helper.sendMessage(textEvent, client, "following you now", false ,false);
            try{
                client.api.moveClient(client.clientID, client.api.getClientInfo(textEvent.getInvokerId()).getChannelId());
            } catch (Exception ex) { }
            new Thread(() -> client.controller.invokeCreateNewClientInstance()).start();
        }
    }
}
