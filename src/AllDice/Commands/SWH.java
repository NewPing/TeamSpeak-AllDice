package AllDice.Commands;

import AllDice.Classes.Outputs;
import AllDice.Controllers.Client;
import AllDice.Helper.DiceHelper;
import AllDice.Helper.Helper;
import AllDice.Helper.LogManager;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

public class SWH extends Command {
    public static String matchPattern = "^!swh(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try{
            String blancOutput = Outputs.blanc_swh_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            int[] randomNumbers = new int[3];
            String reply = "";

            randomNumbers[0] = DiceHelper.getRandomNumber(6);
            randomNumbers[1] = DiceHelper.getRandomNumber(6);
            randomNumbers[2] = DiceHelper.getRandomNumber(6);

            String[] zones = DiceHelper.getSWHZoneOutput(randomNumbers[0] + randomNumbers[1], randomNumbers[2]);
            if (Helper.isNullOrWhitespace(zones[1]))
            {
                blancOutput = blancOutput.replace("\nZusatzwurf W6: $RANDNUMBER2$ - $ZONE1$", "");
            }

            //Output zusammenbauen
            reply += blancOutput;
            reply = reply.replace("$RANDNUMBER0$", String.valueOf(randomNumbers[0]));
            reply = reply.replace("$RANDNUMBER1$", String.valueOf(randomNumbers[1]));
            reply = reply.replace("$RANDNUMBER2$", String.valueOf(randomNumbers[2]));
            reply = reply.replace("$SUM$", String.valueOf(randomNumbers[0] + randomNumbers[1]));

            reply = reply.replace("$ZONE0$", zones[0]);
            reply = reply.replace("$ZONE1$", zones[1]);

            Helper.sendMessage(textEvent, client, reply, false);
        } catch (Exception ex){
            Helper.sendMessage(textEvent, client, "An error has occurred...\nPlease try again with different inputs", false);
            LogManager.log("Error in SWH with input: " + textEvent.getMessage() + "\n\n" + ex);
        }

    }
}