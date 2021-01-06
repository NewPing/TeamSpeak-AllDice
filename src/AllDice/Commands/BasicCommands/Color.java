package AllDice.Commands.BasicCommands;

import AllDice.Controllers.ClientController;
import AllDice.Helper.FileIO;
import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.List;

public class Color extends Command {
    public static String matchPattern = "^!color(:?.+)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        List<String> inputWords = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "[^\\s]+");
        if (inputWords.size() > 1){
            Helper.userColor.add(textEvent.getInvokerUniqueId(), inputWords.get(1));
            Helper.sendMessage(textEvent, clientController, "new color '" + inputWords.get(1) + "' set...", false);
        } else {
            Helper.userColor.remove(textEvent.getInvokerUniqueId());
            Helper.sendMessage(textEvent, clientController, "color parameter removed...", false);
        }

        try{
            FileIO.serializeToFile("usercolors.json", Helper.userColor);
        } catch (Exception ex){
            Logger.log.severe("Error in: execute (color) while writing json ... \n" + ex);
        }
    }
}
