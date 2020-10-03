package AllDice.Commands;

import AllDice.Controllers.ClientController;
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
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        if (textEvent.getInvokerId() == clientController.followClientID){
            Helper.sendMessage(textEvent, clientController, "leaving...", false);
            clientController.sessionController.clientLeave(clientController.clientID);
            clientController.query.exit();
        } else {
            Helper.sendMessage(textEvent, clientController, "Cant force me to leave, when you are not the one i m following...", false);
        }
    }
}