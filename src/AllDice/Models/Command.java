package AllDice.Models;

import AllDice.Controllers.Client;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public abstract class Command {
    public abstract boolean check(String input);
    public abstract void execute(TextMessageEvent textEvent, Client client);
}
