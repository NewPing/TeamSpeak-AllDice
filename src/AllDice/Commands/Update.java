package AllDice.Commands;

import AllDice.Controllers.Client;
import AllDice.Helper.FileIO;
import AllDice.Helper.Helper;
import AllDice.Helper.LogManager;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class Update extends Command {
    public static String matchPattern = "^!update(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        String reply = "Running Update Procedure...";

        String filename = "TeamSpeak3-AllDice-Query2.jar";
        try{
            if (FileIO.exists(filename)){
                FileIO.runJavaProcess(filename);
            }
        } catch (Exception ex){
            LogManager.log("Error in Update\n\n" + ex);
        }

        Helper.sendMessage(textEvent, client, reply, false);
    }
}