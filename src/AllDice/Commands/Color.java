package AllDice.Commands;

import AllDice.Client;
import AllDice.Helper.FileIO;
import AllDice.Helper.Helper;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.List;

public class Color extends Command {
    public static String matchPattern = "^!color(:?.+)?$";

    @Override
    public boolean check(String input) {
        return input.matches(matchPattern);
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        List<String> inputWords = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "[^\\s]+");
        if (inputWords.size() > 1){
            Helper.userColor.add(textEvent.getInvokerUniqueId(), inputWords.get(1));
            Helper.sendMessage(textEvent, client, "new color '" + inputWords.get(1) + "' set...", true ,false);
        } else {
            Helper.userColor.remove(textEvent.getInvokerUniqueId());
            Helper.sendMessage(textEvent, client, "color parameter removed...", true ,false);
        }

        try{
            FileIO.serializeToFile("usercolors.json", Helper.userColor);
        } catch (Exception ex){
            System.out.println("Exception in: execute (color) while writing json ... \n" + ex);
        }
    }
}
