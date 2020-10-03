package AllDice.Commands;

import AllDice.Classes.Logger;
import AllDice.Controllers.ClientController;
import AllDice.Helper.*;
import AllDice.Models.Command;
import AllDice.Models.UserConfigs;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.List;

public class FateConfig extends Command {
    public static String matchPattern = "^!fconfig .+ .+(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        try{
            if (textEvent.getInvokerUniqueId().equals(clientController.followClientUniqueID)){
                List<String> inputWords = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "[^\\s]+");
                if (inputWords.size() >= 2 && inputWords.get(1).toLowerCase().matches("(abilityhigh|abilitylow|outcomehigh)")){
                    if (inputWords.size() >= 3){
                        String param = inputWords.get(1);
                        String newValue = "";
                        for(int i = 2; i < inputWords.size(); i++){
                            newValue += inputWords.get(i);
                        }

                        UserConfigs.UserConfig userConfig = Helper.getUserConfig(textEvent.getInvokerUniqueId());
                        if (userConfig == null){
                            userConfig = new UserConfigs.UserConfig();
                        }
                        if (param.toLowerCase().contains("abilityhigh")) {
                            userConfig.fateAbilityHighName = newValue;
                        } else if (param.toLowerCase().contains("abilitylow")) {
                            userConfig.fateAbilityLowName = newValue;
                        } else { //outcome
                            userConfig.fateOutcomeHighName = newValue;
                        }
                        Helper.userConfigs.userConfigs.add(textEvent.getInvokerUniqueId(), userConfig);

                        Helper.sendMessage(textEvent, clientController, "Neuer Parameterwert für '" + param + "' auf '" + newValue + "' gesetzt...", false);
                        FileIO.serializeToFile("userconfigs.json", Helper.userConfigs);
                    } else if (inputWords.size() == 2) {
                        String param = inputWords.get(1);

                        UserConfigs.UserConfig userConfig = Helper.getUserConfig(textEvent.getInvokerUniqueId());
                        if (userConfig == null){
                            userConfig = new UserConfigs.UserConfig();
                        }
                        if (param.toLowerCase().contains("abilityhigh")) {
                            userConfig.fateAbilityHighName = null;
                        } else if (param.toLowerCase().contains("abilitylow")) {
                            userConfig.fateAbilityLowName = null;
                        } else { //outcome
                            userConfig.fateOutcomeHighName = null;
                        }
                        Helper.userConfigs.userConfigs.add(textEvent.getInvokerUniqueId(), userConfig);

                        Helper.sendMessage(textEvent, clientController, "Neuer Parameterwert für '" + param + "' auf Standardwert zurückgesetzt...", false);
                        FileIO.serializeToFile("userconfigs.json", Helper.userConfigs);
                    }
                } else {
                    Helper.sendMessage(textEvent, clientController, "Syntax Error : Anzahl Eingabeparameter müssen >= 2 sein und abilityhigh, abilitylow oder outcomehigh als parameter enthalten!", false);
                }
            } else {
                Helper.sendMessage(textEvent, clientController, "Command Error : Nur der Eigentümer dieser Würfel darf Sie zinken...", false);
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, clientController, "An error has occurred...\nPlease try again with different inputs", false);
            Logger.log.severe("Error in FateConfig with input: " + textEvent.getMessage() + "\n\n" + ex);
        }
    }
}