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

public class SWW extends Command {
    public static String matchPattern = "^!sww[0-9]+((:?((:?\\+)?|(:?\\-)?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try{
            String blancOutput = Outputs.blanc_sww_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            int[] inputNumbers = null;
            Tuple<Integer, String> explodingDice0 = null;
            Tuple<Integer, String> explodingDice1 = null;
            String reply = "";

            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");
            inputNumbers = new int[2];
            inputNumbers[1] = 0;
            for (int i = 0; i < values.size(); i++)
            {
                inputNumbers[i] = Integer.parseInt(values.get(i));
            }

            if (inputNumbers[0] < 2)
            {
                Helper.sendMessage(textEvent, client, "Syntax Error : Eingabe muss größer als 1 sein!", false);
            }
            else
            {
                explodingDice0 = DiceHelper.getExplodingDice(inputNumbers[0]);
                explodingDice1 = DiceHelper.getExplodingDice(6);
                //Output zusammenbauen
                if (textEvent.getMessage().toLowerCase().contains("-") || textEvent.getMessage().toLowerCase().contains("+"))
                {
                    if (textEvent.getMessage().toLowerCase().contains("-")) //nachricht enthält minus
                    {
                        inputNumbers[1] = -inputNumbers[1];  //letzte zahl umkehren
                        blancOutput = blancOutput.replace("+", "");
                    }
                }
                else
                {
                    blancOutput = blancOutput.replace("+$ADD$ = $RESULT0$", "");
                    blancOutput = blancOutput.replace("+$ADD$ = $RESULT1$", "");
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
                reply = reply.replace("$INPUTNUMBER$", String.valueOf(inputNumbers[0]));
                reply = reply.replace("$RANDNUMBERS0$", explodingDice0.Item2);
                reply = reply.replace("$RANDNUMBERS1$", explodingDice1.Item2);
                reply = reply.replace("$SUM0$", String.valueOf(explodingDice0.Item1));
                reply = reply.replace("$SUM1$", String.valueOf(explodingDice1.Item1));
                reply = reply.replace("$ADD$", String.valueOf(inputNumbers[1]));

                reply = reply.replace("$RESULT0$", String.valueOf(explodingDice0.Item1 + inputNumbers[1]));
                reply = reply.replace("$RESULT1$", String.valueOf(explodingDice1.Item1 + inputNumbers[1]));

                if (explodingDice0.Item1 == explodingDice1.Item1 && explodingDice0.Item1 == 1)
                {
                    reply = reply.replace("$OUTPUT0$", "Kritischer Fehlschlag!");
                    reply = reply.replace("$OUTPUT1$", "Kritischer Fehlschlag!");
                } else
                {
                    reply = reply.replace("$OUTPUT0$", DiceHelper.getSWResultOutput(explodingDice0.Item1 + inputNumbers[1]));
                    reply = reply.replace("$OUTPUT1$", DiceHelper.getSWResultOutput(explodingDice1.Item1 + inputNumbers[1]));
                }

                Helper.sendMessage(textEvent, client, reply, false);
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, client, "An error has occurred...\nPlease try again with different inputs", false);
            Logger.log("Error in SWW with input: " + textEvent.getMessage() + "\n\n" + ex);
        }

    }
}