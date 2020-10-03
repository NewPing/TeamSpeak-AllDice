package AllDice.Commands;

import AllDice.Classes.Outputs;
import AllDice.Controllers.ClientController;
import AllDice.Helper.DiceHelper;
import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.ArrayList;

public class ST extends Command {
    public static String matchPattern = "^!st[0-9]+,[0-9]+,[0-9]+(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        starTrek(textEvent, clientController, false);
    }

    public static void starTrek(TextMessageEvent textEvent, ClientController clientController, boolean isFocus){
        try{
            String blancOutput = Outputs.blanc_st_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            int[] inputNumbers = null;
            String reply = "";

            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");
            inputNumbers = new int[values.size()];
            for (int i = 0; i < values.size(); i++)
            {
                inputNumbers[i] = Integer.parseInt(values.get(i));
            }

            int dies = inputNumbers[0];
            int threshold = inputNumbers[1];
            int difficulty = inputNumbers[2];

            if (dies < 1) {
                Helper.sendMessage(textEvent, clientController, "Syntax Error : Eingabewert 1 muss größer oder gleich 1 sein!", false);
            } else if (threshold < 1) {
                Helper.sendMessage(textEvent, clientController, "Syntax Error : Eingabewert 2 muss größer oder gleich 1 sein!", false);
            } else if (difficulty < 0){
                Helper.sendMessage(textEvent, clientController, "Syntax Error : Eingabewert 3 muss größer oder gleich 0 sein!", false);
            } else {
                String result = "";
                int successes = 0;
                int misses = 0;
                int complications = 0;

                int[] randomNumbers = new int[dies];
                for(int i = 0; i < dies; i++){
                    randomNumbers[i] = DiceHelper.getRandomNumber(20);
                    if (randomNumbers[i] <= threshold){
                        if (randomNumbers[i] == 1){
                            successes += 2;
                        } else {
                            if (isFocus){
                                successes += 2;
                            } else {
                                successes++;
                            }
                        }
                    } else {
                        misses++;
                    }
                    if (randomNumbers[i] == 20){
                        complications++;
                    }
                }

                String outputDies = String.valueOf(randomNumbers[0]);
                for(int i = 1; i < randomNumbers.length; i++){
                    outputDies += ", " + randomNumbers[i];
                }

                if (successes >= difficulty){
                    result = "[b]Gelungen[/b]";
                    if (successes - difficulty > 0){
                        result += ", Momentum " + (successes - difficulty);
                    }
                } else {
                    result = "[b]Misslungen[/b]";
                }
                if (complications > 0){
                    result += ", [b]Komplikationen " + complications + "[/b]";
                }

                reply = blancOutput;
                reply = reply.replace("$dies$", outputDies);
                reply = reply.replace("$successes$", String.valueOf(successes));
                reply = reply.replace("$misses$", String.valueOf(misses));
                reply = reply.replace("$difficulty$", String.valueOf(difficulty));
                reply = reply.replace("$result$", result);

                Helper.sendMessage(textEvent, clientController, reply, false);
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, clientController, "An error has occurred...\nPlease try again with different inputs", false);
            Logger.log.severe("Error in starTrek with input: " + textEvent.getMessage() + "\n\n" + ex);
        }
    }
}