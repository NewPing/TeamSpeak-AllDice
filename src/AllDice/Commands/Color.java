package AllDice.Commands;

import AllDice.Controllers.Client;
import AllDice.Helper.FileIO;
import AllDice.Helper.Helper;
import AllDice.Helper.LogManager;
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
    public void execute(TextMessageEvent textEvent, Client client) {
        List<String> inputWords = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "[^\\s]+");
        if (inputWords.size() > 1){
            Helper.userColor.add(textEvent.getInvokerUniqueId(), inputWords.get(1));
            Helper.sendMessage(textEvent, client, "new color '" + inputWords.get(1) + "' set...", false);
        } else {
            Helper.userColor.remove(textEvent.getInvokerUniqueId());
            Helper.sendMessage(textEvent, client, "color parameter removed...", false);
        }

        try{
            FileIO.serializeToFile("usercolors.json", Helper.userColor);
        } catch (Exception ex){
            LogManager.log("Exception in: execute (color) while writing json ... \n" + ex);
        }
    }
}
