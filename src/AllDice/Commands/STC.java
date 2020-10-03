package AllDice.Commands;

import AllDice.Classes.Outputs;
import AllDice.Controllers.ClientController;
import AllDice.Helper.DiceHelper;
import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.ArrayList;

public class STC extends Command {
    public static String matchPattern = "^!stc[0-9]+(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        try{
            String blancOutput = Outputs.blanc_stc_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            int inputNumber;
            String reply = "";

            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");
            inputNumber = Integer.parseInt(values.get(0));

            if (inputNumber < 1) {
                Helper.sendMessage(textEvent, clientController, "Syntax Error : Eingabewert muss größer oder gleich 1 sein!", false);
            } else {
                String outputDies = "";
                int sum = 0;
                int effects = 0;

                int[] randomNumbers = new int[inputNumber];
                for(int i = 0; i < inputNumber; i++){
                    randomNumbers[i] = DiceHelper.getRandomNumber(6);

                    switch (randomNumbers[i]){
                        case 1:
                            sum++;
                            outputDies += "1, ";
                            break;
                        case 2:
                            sum += 2;
                            outputDies += "2, ";
                            break;
                        case 3:
                        case 4:
                            outputDies += "0, ";
                            break;
                        case 5:
                        case 6:
                            sum++;
                            effects++;
                            outputDies += "1+E, ";
                            break;
                    }
                }

                outputDies = outputDies.substring(0, outputDies.length()-2);

                reply = blancOutput;
                reply = reply.replace("$dies$", outputDies);
                reply = reply.replace("$sum$", String.valueOf(sum));
                reply = reply.replace("$effects$", String.valueOf(effects));

                Helper.sendMessage(textEvent, clientController, reply, false);
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, clientController, "An error has occurred...\nPlease try again with different inputs", false);
            Logger.log.severe("Error in STC with input: " + textEvent.getMessage() + "\n\n" + ex);
        }
    }
}