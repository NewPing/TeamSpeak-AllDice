package AllDice.Commands;

import AllDice.Classes.Outputs;
import AllDice.Controllers.Client;
import AllDice.Helper.DiceHelper;
import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import AllDice.Classes.Implementations.Tuple;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.ArrayList;

public class SWD extends Command {
    public static String matchPattern = "^!swd[0-9]+,[0-9]+(:?(,)?)((:?((:?\\+)?|(:?\\-)?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try{
            String blancOutput = Outputs.blanc_swd_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            int[] inputNumbers = null;
            Tuple<Integer, String> explodingDice0 = null;
            Tuple<Integer, String> explodingDice1 = null;
            String reply = "";

            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");
            inputNumbers = new int[3];
            inputNumbers[2] = 0;
            for (int i = 0; i < values.size(); i++)
            {
                inputNumbers[i] = Integer.parseInt(values.get(i));
            }

            if (inputNumbers[0] < 2 || inputNumbers[1] < 2)
            {
                Helper.sendMessage(textEvent, client, "Syntax Error : Eingabe muss größer als 1 sein!", false);
            }
            else {
                explodingDice0 = DiceHelper.getExplodingDice(inputNumbers[0]);
                explodingDice1 = DiceHelper.getExplodingDice(inputNumbers[1]);
                //Output zusammenbauen
                if (Helper.getRegexMatches(textEvent.getMessage(), ",").size() == 2 || textEvent.getMessage().contains("+") || textEvent.getMessage().contains("-")) {
                    if (textEvent.getMessage().toLowerCase().contains("-")) //nachricht enthält minus
                    {
                        inputNumbers[2] = -inputNumbers[2];  //letzte zahl umkehren
                        blancOutput = blancOutput.replace("+$ADD$", "$ADD$");
                    }
                } else {
                    blancOutput = blancOutput.replace("+$ADD$ ", "");
                }
                if (!explodingDice0.Item2.contains("+")) //Addition wird nicht benötigt
                {
                    blancOutput = blancOutput.replace("Rechnung: $RANDNUMBERS0$\nSumme", "Ergebnis");
                }
                if (!explodingDice1.Item2.contains("+")) //Addition wird nicht benötigt
                {
                    blancOutput = blancOutput.replace("Rechnung: $RANDNUMBERS1$\nSumme", "Ergebnis");
                }

                reply += blancOutput;
                reply = reply.replace("$INPUTNUMBER0$", String.valueOf(inputNumbers[0]));
                reply = reply.replace("$INPUTNUMBER1$", String.valueOf(inputNumbers[1]));
                reply = reply.replace("$RANDNUMBERS0$", explodingDice0.Item2);
                reply = reply.replace("$RANDNUMBERS1$", explodingDice1.Item2);
                reply = reply.replace("$SUM0$", String.valueOf(explodingDice0.Item1));
                reply = reply.replace("$SUM1$", String.valueOf(explodingDice1.Item1));
                reply = reply.replace("$ADD$", String.valueOf(inputNumbers[2]));

                reply = reply.replace("$RESULT$", String.valueOf((explodingDice0.Item1 + explodingDice1.Item1 + inputNumbers[2])));

                Helper.sendMessage(textEvent, client, reply, false);
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, client, "An error has occurred...\nPlease try again with different inputs", false);
            Logger.log("Error in SWD with input: " + textEvent.getMessage() + "\n\n" + ex);
        }

    }
}