package AllDice.Commands;

import AllDice.Classes.Outputs;
import AllDice.Controllers.Client;
import AllDice.Helper.DiceHelper;
import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.ArrayList;

public class DIM extends Command {
    public static String matchPattern = "^!d((:?((:?\\+)?|(:?\\-)?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try{
            String blancOutput = Outputs.blanc_dim_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            int inputNumber = 0;
            int randomNumber = DiceHelper.getRandomNumber(30);
            int sum = 0;
            String reply = "";

            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");
            if (values.size() == 1){
                inputNumber = Integer.parseInt(values.get(0));
            }

            if (inputNumber == 0){
                blancOutput = blancOutput.replace(" +$INPUTNUMBER$", "");
            } else {
                if (textEvent.getMessage().toLowerCase().contains("-") || textEvent.getMessage().toLowerCase().contains("+"))
                {
                    if (textEvent.getMessage().toLowerCase().contains("-")) //nachricht enthält minus
                    {
                        inputNumber = -inputNumber;  //letzte zahl umkehren
                        blancOutput = blancOutput.replace("+", "");
                    }
                }
            }

            sum = randomNumber + inputNumber;
            String result = "";
            if (sum <= 1){
                result = "Dimensions Erolg";
            } else if (sum <= 5){
                result = "Extremer Erfolg";
            } else if (sum <= 10){
                result = "Erfolg";
            } else if (sum <= 15){
                result = "Leichter Erfolg";
            } else if (sum <= 20){
                result = "Leichter Misserfolg";
            } else if (sum <= 25){
                result = "Misserfolg";
            } else if (sum <= 29){
                result = "Extremer Misserfolg";
            } else {
                result = "Dimensions Misserfolg";
            }

            reply += blancOutput;
            reply = reply.replace("$INPUTNUMBER$", String.valueOf(inputNumber));
            reply = reply.replace("$RANDNUMBER$", String.valueOf(randomNumber));
            reply = reply.replace("$RESSUM$", String.valueOf(sum));
            reply = reply.replace("$RESULT$", result);

            Helper.sendMessage(textEvent, client, reply, false);
        } catch (Exception ex){
            Helper.sendMessage(textEvent, client, "An error has occurred...\nPlease try again with different inputs", false);
            Logger.log.severe("Error in DIM with input: " + textEvent.getMessage() + "\n\n" + ex);
        }

    }
}