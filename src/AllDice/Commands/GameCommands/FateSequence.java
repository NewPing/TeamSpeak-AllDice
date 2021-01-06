package AllDice.Commands.GameCommands;

import AllDice.Classes.BlancOutputs;
import AllDice.Controllers.ClientController;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class FateSequence extends Command {
    public static String matchPattern = "^!f(a|d)(:?(?:(?:[0-9]+((:?((:?\\+)?|(:?\\-)?)[0-9]+)?))?)?)?(?: +)?$";
    public static String matchPatternAttacker = "^!fa(:?(?:(?:[0-9]+((:?((:?\\+)?|(:?\\-)?)[0-9]+)?))?)?)?(?: +)?$";
    private Fate fate;

    public FateSequence(Fate _fate){
        fate = _fate;
    }

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        if (textEvent.getMessage().toLowerCase().matches(matchPatternAttacker)){
            //attack
            fate.fateThrow(textEvent, clientController, 1, BlancOutputs.blanc_fate_Sequence_Output);
        } else {
            //defend
            fate.fateThrow(textEvent, clientController, 2, BlancOutputs.blanc_fate_Sequence_Output);
        }
    }
}