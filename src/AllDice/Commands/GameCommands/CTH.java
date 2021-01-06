package AllDice.Commands.GameCommands;

import AllDice.Classes.BlancOutputs;
import AllDice.Controllers.ClientController;
import AllDice.Helper.DiceHelper;
import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.ArrayList;

public class CTH extends Command {
    public static String matchPattern = "^!cth[0-9]+((:?((:?\\+)?|(:?\\-)?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        try{
            String blancOutput = BlancOutputs.blanc_cth_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            int[] inputNumbers = null;
            int randomNumber = DiceHelper.getRandomNumber(100);
            String reply = "";

            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");
            inputNumbers = new int[2];
            inputNumbers[0] = 0;
            inputNumbers[1] = 0;
            for (int i = 0; i < values.size(); i++)
            {
                inputNumbers[i] = Integer.parseInt(values.get(i));
            }

            if (inputNumbers[1] == 0){
                blancOutput = blancOutput.replace("( $INPUTNUMBERS0$ +$INPUTNUMBERS1$ )", "$INPUTNUMBERS0$");
            } else {
                if (textEvent.getMessage().toLowerCase().contains("-") || textEvent.getMessage().toLowerCase().contains("+"))
                {
                    if (textEvent.getMessage().toLowerCase().contains("-")) //nachricht enthält minus
                    {
                        inputNumbers[1] = -inputNumbers[1];  //letzte zahl umkehren
                        blancOutput = blancOutput.replace("+", "");
                    }
                }
            }

            if (inputNumbers[0] < 0)
            {
                Helper.sendMessage(textEvent, clientController, "Syntax Error : Eingabe muss größer als 0 sein!", false);
            }
            else
            {
                String result = "";
                if (randomNumber <= inputNumbers[0] + inputNumbers[1]){
                    result = "Erfolg!";
                    if (randomNumber <= (inputNumbers[0]  + inputNumbers[1]) / 2){
                        result = "Schwerer Erfolg!";
                        if (randomNumber <= (inputNumbers[0] + inputNumbers[1]) / 5){
                            result = "Kritischer Erfolg!";
                        }
                    }
                } else {
                    result = "Misserfolg!";
                    if (randomNumber >= 96){
                        result = "Patzer!";
                        if (randomNumber == 100){
                            result = "Kritischer Patzer!";
                        }
                    }
                }

                reply += blancOutput;
                reply = reply.replace("$INPUTNUMBERS0$", String.valueOf(inputNumbers[0]));
                reply = reply.replace("$INPUTNUMBERS1$", String.valueOf(inputNumbers[1]));
                reply = reply.replace("$RANDNUMBER$", String.valueOf(randomNumber));
                reply = reply.replace("$RESULT$", result);

                Helper.sendMessage(textEvent, clientController, reply, false);
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, clientController, "An error has occurred...\nPlease try again with different inputs", false);
            Logger.log.severe("Error in CTH with input: " + textEvent.getMessage() + "\n\n" + ex);
        }

    }
}