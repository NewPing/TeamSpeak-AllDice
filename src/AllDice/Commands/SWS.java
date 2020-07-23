package AllDice.Commands;

import AllDice.Controllers.Client;
import AllDice.Helper.Helper;
import AllDice.Helper.LogManager;
import AllDice.Helper.Tuple;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.ArrayList;

public class SWS extends Command {
    public static String matchPattern = "!sws[0-9]+((:?((:?\\+)?|(:?\\-)?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.matches(matchPattern);
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try{
            String blancOutput = Helper.blanc_sws_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            int[] inputNumbers = null;
            Tuple<Integer, String> explodingDice0 = null;
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
                explodingDice0 = Helper.getExplodingDice(inputNumbers[0]);
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
                }
                if (!explodingDice0.Item2.contains("+")) //Addition wird nicht benötigt
                {
                    blancOutput = blancOutput.replace("Rechnung: $RANDNUMBERS0$\nSumme", "Ergebnis");
                }

                reply += blancOutput;
                reply = reply.replace("$INPUTNUMBER$", String.valueOf(inputNumbers[0]));
                reply = reply.replace("$RANDNUMBERS0$", String.valueOf(explodingDice0.Item2));
                reply = reply.replace("$SUM0$", String.valueOf(explodingDice0.Item1));
                reply = reply.replace("$ADD$", String.valueOf(inputNumbers[1]));

                reply = reply.replace("$RESULT0$", String.valueOf(explodingDice0.Item1 + inputNumbers[1]));

                if (explodingDice0.Item1 == 1)
                {
                    reply = reply.replace("$OUTPUT0$", "Kritischer Fehlschlag!");
                }
                else
                {
                    reply = reply.replace("$OUTPUT0$", Helper.getSWResultOutput(explodingDice0.Item1 + inputNumbers[1]));
                }

                Helper.sendMessage(textEvent, client, reply, false);
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, client, "An error has occurred...\nPlease try again with different inputs", false);
            LogManager.log("Error in SWS with input: " + textEvent.getMessage() + "\n\n" + ex);
        }

    }
}