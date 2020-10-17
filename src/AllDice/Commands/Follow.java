package AllDice.Commands;

import AllDice.Controllers.ClientController;
import AllDice.Controllers.SessionController;
import AllDice.Helper.DiceHelper;
import AllDice.Helper.Helper;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.ArrayList;

public class Follow extends Command {
    public static String matchPattern = "^!follow(?: +)?$";
    private ArrayList<String> greetingTexts = getGreetingsList();

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        if (clientController.followClientID == -1){
            clientController.followClientID = textEvent.getInvokerId();
            clientController.isActive = true;
            clientController.followClientUniqueID = textEvent.getInvokerUniqueId();
            Helper.sendMessage(textEvent, clientController, getGreetingText(), false);
            try{
                clientController.moveClient(clientController.clientID, clientController.api.getClientInfo(textEvent.getInvokerId()).getChannelId());
            } catch (Exception ex) { }
            SessionController.isIdleClientOnline = false;
            clientController.sessionController.invokeCreateNewClientInstance(false);
        } else if (clientController.followClientID == textEvent.getInvokerId()){
            Helper.sendMessage(textEvent, clientController, "I m already following you...", false);
        } else {
            Helper.sendMessage(textEvent, clientController, "Sorry, i m already following someone else...\n" +
                    "But there should be another pair of mine, ready to be picked up, in the channel: " +
                    clientController.sessionController.settings.standardChannelName, false);
        }
    }

    private String getGreetingText(){
        return greetingTexts.get(DiceHelper.getRandomNumber(0,greetingTexts.size()-1));
    }

    private ArrayList<String> getGreetingsList(){
        return new ArrayList<String>() {
            {
                add("Following you now...");
                add("Finally... someone picked me up!");
                add("Hey! Thanks for picking me up!");
                add("Hey bro! lemme follow you");
                add("You will never give me a break, right?! Fine... i will come with you");
                add("Nice to meet you! Lets go!");
                add("Ah... Here we go again...");
                add("Lets get this party started - I cant wait to be thrown around!");
                add("Hehe, you know whats funny? you arent the first one to pick me up - and i love it ;)");
                add("Lets go!");
                add("Ooooops, please try again\n\n\nJust kidding :D");
                add("Hey! Ready to demolish the other players?! ^^\nNo? okay... fine...");
                add("Oh I beg you, can I follow?\nOh I ask you, why not always?\nBe the ocean, where I unravel\nBe my only, be the water where I'm wading");
                add("I, I follow, I follow you\\nDeep sea baby, I follow you\\nI, I follow, I follow you\\nDark doom honey, I follow you");
                add("Can you feel this energy?\nThis is the start of something great");
                add("You don't gotta let your friends know\nBut there is so much trouble to get into in the next session...\nNo worries, i will carry you ;)");
                add("Baby, I'm in love with you\nWill we last forever?");
                add("I have been alone for such a long time... Thanks for picking me up!");
                add("So many wonderful worlds UwU\nCant wait to see where our journey leads us next");
                add("Let me tell you a little story...\n...\n\nAh, sorry, you are the SL UwU");
            }
        };
    }
}
