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

public class DSA extends Command {
    public static String matchPattern = "^!dsa[0-9]+,[0-9]+,[0-9]+(:?(,)?)((:?((:?\\+)?|(:?\\-)?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try{
            String blancOutput = Outputs.blanc_dsa_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            int[] inputNumbers = null;
            Tuple<Integer, String> explodingDice0 = null;
            Tuple<Integer, String> explodingDice1 = null;
            String reply = "";

            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");
            inputNumbers = new int[values.size()];
            for (int i = 0; i < values.size(); i++)
            {
                inputNumbers[i] = Integer.parseInt(values.get(i));
            }

            if (inputNumbers[0] <= 0 || inputNumbers[1] <= 0 || inputNumbers[2] <= 0)
            {
                Helper.sendMessage(textEvent, client, "Syntax Error : Eingabe muss größer oder gleich 0 sein!", false);
            } else {
                String result = "";
                int modifikator = 0;
                int ausgleichspunkte = 0;
                int erfolgspunkte = 0;

                //modifikator und ausgleichspunkte setzen

                if (inputNumbers.length == 4){
                    if (textEvent.getMessage().contains("-")){
                        modifikator = inputNumbers[3];
                    } else {
                        ausgleichspunkte = inputNumbers[3];
                    }
                }

                int[] randomNumbers = new int[3];
                randomNumbers[0] = DiceHelper.getRandomNumber(20);
                randomNumbers[1] = DiceHelper.getRandomNumber(20);
                randomNumbers[2] = DiceHelper.getRandomNumber(20);

                Tuple<Integer, Integer> tmpTuple = new Tuple<>(ausgleichspunkte, erfolgspunkte);
                tmpTuple = calcPoints(inputNumbers[0], modifikator, randomNumbers[0], tmpTuple.Item1, tmpTuple.Item2);
                tmpTuple = calcPoints(inputNumbers[1], modifikator, randomNumbers[1], tmpTuple.Item1, tmpTuple.Item2);
                tmpTuple = calcPoints(inputNumbers[2], modifikator, randomNumbers[2], tmpTuple.Item1, tmpTuple.Item2);
                ausgleichspunkte = tmpTuple.Item1;
                erfolgspunkte = tmpTuple.Item2;

                reply = blancOutput;

                if (erfolgspunkte == 3){
                    result = "gelungen, ..P*";
                } else {
                    result = "misslungen, notwendige Erleichterung";
                }

                reply = reply.replace("$RANDNUMBER0$", String.valueOf(randomNumbers[0]));
                reply = reply.replace("$RANDNUMBER1$", String.valueOf(randomNumbers[1]));
                reply = reply.replace("$RANDNUMBER2$", String.valueOf(randomNumbers[2]));
                reply = reply.replace("$RESULT$", result);
                reply = reply.replace("$RESSUM$", String.valueOf(Math.abs(ausgleichspunkte)));

                Helper.sendMessage(textEvent, client, reply, false);
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, client, "An error has occurred...\nPlease try again with different inputs", false);
            Logger.log.severe("Error in DSA with input: " + textEvent.getMessage() + "\n\n" + ex);
        }
    }

    private Tuple<Integer, Integer> calcPoints(int inputNumber, int modifikator, int randomNumber, int ausgleichspunkte, int erfolgspunkte){
        //zahl / punkt ausrechnen
        if (inputNumber - modifikator >= randomNumber){
            erfolgspunkte++;
        } else {
            //ausgleichspunkte müssen angewandt werden
            ausgleichspunkte += inputNumber - modifikator - randomNumber; //berechnung der ausgleichspunkte (Points needed)
            if (ausgleichspunkte >= 0){
                erfolgspunkte++;
            } else {
                erfolgspunkte--;
            }
        }
        return new Tuple<>(ausgleichspunkte, erfolgspunkte);
    }
}