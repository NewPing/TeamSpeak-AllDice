package AllDice.Commands;

import AllDice.Client;
import AllDice.Helper.*;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.ArrayList;
import java.util.List;

public class FateConfig extends Command {
    public static String matchPattern = "^!fconfig .+ .+(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.matches(matchPattern);
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try{
            if (textEvent.getInvokerUniqueId().equals(client.followClientUniqueID)){
                List<String> inputWords = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "[^\\s]+");
                if (inputWords.size() >= 2 && inputWords.get(1).toLowerCase().matches("(abilityhigh|abilitylow|outcomehigh)")){
                    if (inputWords.size() >= 3){
                        String param = inputWords.get(1);
                        String newValue = "";
                        for(int i = 2; i < inputWords.size(); i++){
                            newValue += inputWords.get(i);
                        }

                        UserConfig userConfig = Helper.getUserConfig(textEvent.getInvokerUniqueId());
                        if (userConfig == null){
                            userConfig = new UserConfig();
                        }
                        if (param.toLowerCase().contains("abilityhigh")) {
                            userConfig.fateAbilityHighName = newValue;
                        } else if (param.toLowerCase().contains("abilitylow")) {
                            userConfig.fateAbilityLowName = newValue;
                        } else { //outcome
                            userConfig.fateOutcomeHighName = newValue;
                        }
                        Helper.userConfigs.userConfigs.add(textEvent.getInvokerUniqueId(), userConfig);

                        Helper.sendMessage(textEvent, client, "Neuer Parameterwert für '" + param + "' auf '" + newValue + "' gesetzt...", true ,false);
                        FileIO.serializeToFile("userconfigs.json", Helper.userConfigs);
                    } else if (inputWords.size() == 2) {
                        String param = inputWords.get(1);

                        UserConfig userConfig = Helper.getUserConfig(textEvent.getInvokerUniqueId());
                        if (userConfig == null){
                            userConfig = new UserConfig();
                        }
                        if (param.toLowerCase().contains("abilityhigh")) {
                            userConfig.fateAbilityHighName = null;
                        } else if (param.toLowerCase().contains("abilitylow")) {
                            userConfig.fateAbilityLowName = null;
                        } else { //outcome
                            userConfig.fateOutcomeHighName = null;
                        }
                        Helper.userConfigs.userConfigs.add(textEvent.getInvokerUniqueId(), userConfig);

                        Helper.sendMessage(textEvent, client, "Neuer Parameterwert für '" + param + "' auf Standardwert zurückgesetzt...", true, false);
                        FileIO.serializeToFile("userconfigs.json", Helper.userConfigs);
                    }
                } else {
                    Helper.sendMessage(textEvent, client, "Syntax Error : Anzahl Eingabeparameter müssen >= 2 sein und abilityhigh, abilitylow oder outcomehigh als parameter enthalten!", false ,false);
                }
            } else {
                Helper.sendMessage(textEvent, client, "Command Error : Nur der Eigentümer dieser Würfel darf Sie zinken...", false ,false);
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, client, "An error has occurred...\nPlease try again with different inputs", false ,false);
            System.out.println("Error in FateConfig with input: " + textEvent.getMessage() + "\n\n" + ex);
        }
    }
}