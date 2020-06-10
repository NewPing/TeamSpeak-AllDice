package AllDice.Commands;

import AllDice.Client;
import AllDice.Helper.Helper;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class PM extends Command {
    public static String matchPattern = "^!pm(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.matches(matchPattern);
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        Helper.sendMessage(textEvent, client, "Opened private chat...", false, true);
    }
}