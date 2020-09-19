package AllDice.Commands;

import AllDice.Classes.Outputs;
import AllDice.Controllers.Client;
import AllDice.Helper.DiceHelper;
import AllDice.Helper.Helper;
import AllDice.Helper.LogManager;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.ArrayList;

public class W extends Command {
    public static String matchPattern = "^!((?:[0-9])?)+w[0-9]+((:?((:?\\+)?|(:?\\-)?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try{
            String blancOutput = Outputs.blanc_w_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            int[] inputNumbers = null;
            int[] randNumbers = null;
            String reply = "";
            int sum = 0;

            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");
            inputNumbers = new int[values.size()];
            for (int i = 0; i < values.size(); i++)
            {
                inputNumbers[i] = Integer.parseInt(values.get(i));
            }
            randNumbers = new int[inputNumbers[0]];

            if (inputNumbers.length == 1)
            {
                if (inputNumbers[0] < 1)
                {
                    Helper.sendMessage(textEvent, client, "Syntax Error : Eingabe muss größer als 0 sein!", false);
                }
                else
                {
                    //______________Logik w3______________________
                    randNumbers[0] = DiceHelper.getRandomNumber(inputNumbers[0]);
                    //Output zusammenbauen
                    blancOutput = blancOutput.replace("Rechnung: $RANDNUMBER$ = $SUM$\nSumme: $SUM$+$ADD$\n", "");
                    reply += blancOutput;
                    sum = randNumbers[0];
                    reply = reply.replace("$RESULT$", String.valueOf(sum));

                    Helper.sendMessage(textEvent, client, reply, false);
                }
            }
            else if (inputNumbers.length == 2)
            {
                if (textEvent.getMessage().toLowerCase().contains("+") || textEvent.getMessage().toLowerCase().contains("-"))
                {
                    if (inputNumbers[0] < 1)
                    {
                        Helper.sendMessage(textEvent, client, "Syntax Error : Eingabe muss größer als 0 sein!", false);
                    }
                    else
                    {
                        //______________Logik w3+3______________________
                        if (textEvent.getMessage().toLowerCase().contains("-"))
                        {
                            inputNumbers[1] = -inputNumbers[1];  //letzte zahl umkehren
                            blancOutput = blancOutput.replace("+", "");
                        }
                        randNumbers[0] = DiceHelper.getRandomNumber(inputNumbers[0]);
                        //Output zusammenbauen
                        blancOutput = blancOutput.replace("Rechnung: $RANDNUMBER$ = $SUM$\n", "");
                        reply += blancOutput;
                        reply = reply.replace("$RANDNUMBER$", String.valueOf(randNumbers[0]));
                        sum = randNumbers[0];
                        reply = reply.replace("$SUM$", String.valueOf(sum));
                        reply = reply.replace("$ADD$", String.valueOf(inputNumbers[1]));
                        reply = reply.replace("$RESULT$", String.valueOf(sum + inputNumbers[1]));

                        Helper.sendMessage(textEvent, client, reply, false);
                    }
                }
                else
                {
                    if (inputNumbers[0] < 1 || inputNumbers[1] < 1)
                    {
                        Helper.sendMessage(textEvent, client, "Syntax Error : Eingabe muss größer als 0 sein!", false);
                    }
                    else
                    {
                        //______________Logik 3w3______________________
                        for (int i = 0; i < inputNumbers[0]; i++) //random zahlen generieren
                        {
                            randNumbers[i] = DiceHelper.getRandomNumber(inputNumbers[1]);
                        }
                        //Output zusammenbauen
                        blancOutput = blancOutput.replace("Summe: $SUM$+$ADD$\n", "");
                        blancOutput = blancOutput.replace(" = $SUM$", "");
                        reply += blancOutput;
                        reply = reply.replace("$RANDNUMBER$", randNumbers[0] + "+$RANDNUMBER$");
                        sum = randNumbers[0];
                        for (int i = 1; i < randNumbers.length; i++)
                        {
                            reply = reply.replace("$RANDNUMBER$", randNumbers[i] + "+$RANDNUMBER$");
                            sum += randNumbers[i];
                        }
                        reply = reply.replace("+$RANDNUMBER$", "");
                        reply = reply.replace("$RESULT$", String.valueOf(sum));

                        Helper.sendMessage(textEvent, client, reply, false);
                    }
                }
            }
            else if (inputNumbers.length == 3 || inputNumbers[1] < 1) //3w3+3 OR 3w3-3
            {
                if (inputNumbers[0] < 1)
                {
                    Helper.sendMessage(textEvent, client, "Syntax Error : Eingabe muss größer als 0 sein!", false);
                }
                else
                {
                    if (textEvent.getMessage().toLowerCase().contains("-"))
                    {
                        inputNumbers[2] = 0 - inputNumbers[2];  //letzte zahl umkehren
                        blancOutput = blancOutput.replace("+", "");
                    }
                    //______________Logik 3w3+3______________________
                    for (int i = 0; i < inputNumbers[0]; i++) //random zahlen generieren
                    {
                        randNumbers[i] = DiceHelper.getRandomNumber(inputNumbers[1]);
                    }
                    //Output zusammenbauen
                    reply += blancOutput;
                    reply = reply.replace("$RANDNUMBER$", randNumbers[0] + "+$RANDNUMBER$");
                    sum = randNumbers[0];
                    for (int i = 1; i < randNumbers.length; i++)
                    {
                        reply = reply.replace("$RANDNUMBER$", randNumbers[i] + "+$RANDNUMBER$");
                        sum += randNumbers[i];
                    }
                    reply = reply.replace("+$RANDNUMBER$", "");
                    reply = reply.replace("$SUM$", String.valueOf(sum));
                    reply = reply.replace("$ADD$", String.valueOf(inputNumbers[2]));
                    reply = reply.replace("$RESULT$", String.valueOf(sum + inputNumbers[2]));

                    Helper.sendMessage(textEvent, client, reply, false);
                }
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, client, "An error has occurred...\nPlease try again with different inputs", false);
            LogManager.log("Error in W with input: " + textEvent.getMessage() + "\n\n" + ex);
        }

    }
}