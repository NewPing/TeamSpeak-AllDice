package AllDice.Commands.GameCommands;

import AllDice.Classes.BlancOutputs;
import AllDice.Controllers.ClientController;
import AllDice.Helper.DiceHelper;
import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import AllDice.Models.Command;
import AllDice.Models.UserConfigs;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.ArrayList;

public class Fate extends Command {
    public static String matchPattern = "^!f(:?(?:(?:[0-9]+((:?((:?\\+)?|(:?\\-)?)[0-9]+)?))?(?:,(((:?((:?\\+)?|(:?\\-)?)[0-9]+)?)|f(?:[0-9]+((:?((:?\\+)?|(:?\\-)?)[0-9]+)?))?))?)?)?(?: +)?$";
    public static String passiveThrow = "^!f(:?(a|d))?(:?(?:(?:[0-9]+((:?((:?\\+)?|(:?\\-)?)[0-9]+)?))?(?:,(((:?((:?\\+)?|(:?\\-)?)[0-9]+)?)))?)?)?(?: +)?$";
    private int abilityLastThrow = 0;

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        fateThrow(textEvent, clientController, 0, BlancOutputs.blanc_fate_passive_Output);
    }

    /***
     *
     * @param textEvent
     * @param clientController
     * @param fateSequenceType 0: no fate sequence throw; 1: throw is attacker; 2: throw is defender
     */
    public void fateThrow(TextMessageEvent textEvent, ClientController clientController, int fateSequenceType, String blancOutput){
        String abilityHighName = "Unbeschreibbar";
        String abilityLowName = "Unbeschreibbar schlecht";
        String outcomeHighName = "Unbeschreibbarer Erfolg";

        if (Helper.userConfigs.userConfigs.contains(clientController.followClientUniqueID)){
            UserConfigs.UserConfig userConfig = Helper.userConfigs.userConfigs.get(clientController.followClientUniqueID);
            if (userConfig.fateAbilityHighName != null){
                abilityHighName = userConfig.fateAbilityHighName;
            }
            if (userConfig.fateAbilityLowName != null){
                abilityLowName = userConfig.fateAbilityLowName;
            }
            if (userConfig.fateOutcomeHighName != null){
                outcomeHighName = userConfig.fateOutcomeHighName;
            }
        }

        try{
            if (textEvent.getMessage().matches(passiveThrow)){
                executePassive(textEvent, clientController, outcomeHighName, abilityHighName, abilityLowName, fateSequenceType, blancOutput);
            } else {
                executeActive(textEvent, clientController, outcomeHighName, abilityHighName, abilityLowName);
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, clientController, "An error has occurred...\nPlease try again with different inputs", false);
            Logger.log.severe("Error in Fate with input: " + textEvent.getMessage() + "\n\n" + ex);
        }
    }

    /***
     *
     * @param textEvent
     * @param clientController
     * @param outcomeHighName
     * @param abilityHighName
     * @param abilityLowName
     * @param fateSequenceType 0: no fate sequence throw; 1: first fate throw is attacker; 2: first throw is defender
     */
    private void executePassive(TextMessageEvent textEvent, ClientController clientController, String outcomeHighName, String abilityHighName, String abilityLowName, int fateSequenceType, String blancOutput){
        try{
            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            String reply = "";
            //set default values (if no input is given, just !f)
            int skill = 0;
            int mod = 0;
            int goal = 1;
            if (fateSequenceType != 0){
                goal = abilityLastThrow;
            }

            int[] rolls = rollFates();

            switch (values.size()){
                case 1:
                    if (textEvent.getMessage().contains(",")){
                        if (textEvent.getMessage().split(",")[1].contains("-")){
                            goal = -Integer.parseInt(values.get(0));
                        } else {
                            goal = Integer.parseInt(values.get(0));
                        }
                    } else {
                        skill = Integer.parseInt(values.get(0));
                    }
                    break;
                case 2:
                    skill = Integer.parseInt(values.get(0));
                    if (textEvent.getMessage().contains(",")){
                        if (textEvent.getMessage().split(",")[1].contains("-")){
                            goal = -Integer.parseInt(values.get(1));
                        } else {
                            goal = Integer.parseInt(values.get(1));
                        }
                    } else {
                        if (textEvent.getMessage().split(",")[0].contains("-")){
                            mod = -Integer.parseInt(values.get(1));
                        } else {
                            mod = Integer.parseInt(values.get(1));
                        }
                    }
                    break;
                case 3:
                    skill = Integer.parseInt(values.get(0));
                    if (textEvent.getMessage().split(",")[0].contains("-")){
                        mod = -Integer.parseInt(values.get(1));
                    } else {
                        mod = Integer.parseInt(values.get(1));
                    }
                    if (textEvent.getMessage().split(",")[1].contains("-")){
                        goal = -Integer.parseInt(values.get(2));
                    } else {
                        goal = Integer.parseInt(values.get(2));
                    }
                    break;
            }

            int ability = rolls[4] + skill + mod;
            int outcome = ability - goal;
            int outcomeAttacker = 0;
            int outcomeDefender = 0;
            String resistanceType = "";
            String outcomeNameAttacker = "";
            String outcomeNameDefender = "";

            if (textEvent.getMessage().toLowerCase().matches(FateSequence.matchPatternAttacker))
            {
                resistanceType = "Verteidigungs";
                outcomeAttacker = ability - goal;
                outcomeDefender = goal - ability;
            } else {
                resistanceType = "Angriffs";
                outcomeAttacker = goal - ability;
                outcomeDefender = ability - goal;
            }

            outcomeNameAttacker = DiceHelper.getFateOutcomeName(outcomeAttacker, outcomeHighName);
            outcomeNameDefender = DiceHelper.getFateOutcomeName(outcomeDefender, outcomeHighName);

            abilityLastThrow = ability;

            reply += blancOutput;
            reply = reply.replace("$SKILL$", String.valueOf(skill));
            reply = reply.replace("$MOD$", String.valueOf(mod));
            reply = reply.replace("$RESISTANCETYPE$", resistanceType);
            reply = reply.replace("$RESULT$", String.valueOf(rolls[4]));
            reply = reply.replace("$ABILITY$", String.valueOf(ability));
            reply = reply.replace("$OUTCOME$", String.valueOf(outcome));
            reply = reply.replace("$OUTCOMEATTACKER$", String.valueOf(outcomeAttacker));
            reply = reply.replace("$OUTCOMEDEFENDER$", String.valueOf(outcomeDefender));
            reply = reply.replace("$GOAL$", String.valueOf(goal));
            reply = reply.replace("$ABILITYNAME$", DiceHelper.getFateAbilityName(ability, abilityHighName, abilityLowName));
            reply = reply.replace("$OUTCOMENAME$", DiceHelper.getFateOutcomeName(outcome, outcomeHighName));
            reply = reply.replace("$OUTCOMENAMEATTACKER$", outcomeNameAttacker);
            reply = reply.replace("$OUTCOMENAMEDEFENDER$", outcomeNameDefender);
            reply = reply.replace("$EMOJIS$", DiceHelper.getFateEmojis(rolls));

            Helper.sendMessage(textEvent, clientController, reply, false);
        } catch (Exception ex){
            throw ex;
        }
    }

    private void executeActive(TextMessageEvent textEvent, ClientController clientController, String outcomeHighName, String abilityHighName, String abilityLowName){
        try{
            ArrayList<String> valuesLeft = Helper.getRegexMatches(textEvent.getMessage().toLowerCase().split(",")[0], "\\d+"); //before , (user)
            ArrayList<String> valuesRight = new ArrayList<>();
            if (textEvent.getMessage().contains(",")){
                valuesRight = Helper.getRegexMatches(textEvent.getMessage().toLowerCase().split(",")[1], "\\d+"); //after , (opponent)
            }
            String blancOutput = BlancOutputs.blanc_fate_active_Output;
            blancOutput = blancOutput.replace("$AUTHOR$", textEvent.getInvokerName());
            String reply = "";
            //set default values (if no input is given, just !f)
            int skill = 0;
            int mod = 0;
            int skillop = 0;
            int modop = 0;

            switch (valuesLeft.size()){
                case 1:
                    skill = Integer.parseInt(valuesLeft.get(0));
                    break;
                case 2:
                    skill = Integer.parseInt(valuesLeft.get(0));
                    if (textEvent.getMessage().contains("-")){
                        mod = -Integer.parseInt(valuesLeft.get(1));
                    } else {
                        mod = Integer.parseInt(valuesLeft.get(1));
                    }
                    break;
            }
            switch (valuesRight.size()){
                case 1:
                    skillop = Integer.parseInt(valuesRight.get(0));
                    break;
                case 2:
                    skillop = Integer.parseInt(valuesRight.get(0));
                    if (textEvent.getMessage().contains("-")){
                        modop = -Integer.parseInt(valuesRight.get(1));
                    } else {
                        modop = Integer.parseInt(valuesRight.get(1));
                    }
                    break;
            }

            int[] rolls = rollFates();
            int[] rollsop = rollFates();

            int ability = rolls[4] + skill + mod;
            int abilityop = rollsop[4] + skillop + modop;

            abilityLastThrow = ability;

            int outcome = ability - abilityop;

            reply += blancOutput;
            reply = reply.replace("$SKILL$", String.valueOf(skill));
            reply = reply.replace("$SKILLOPPONENT$", String.valueOf(skillop));
            reply = reply.replace("$MOD$", String.valueOf(mod));
            reply = reply.replace("$MODOPPONENT$", String.valueOf(modop));
            reply = reply.replace("$RESULT$", String.valueOf(rolls[4]));
            reply = reply.replace("$RESULTOPPONENT$", String.valueOf(rollsop[4]));
            reply = reply.replace("$ABILITY$", String.valueOf(ability));
            reply = reply.replace("$ABILITYOPPONENT$", String.valueOf(abilityop));
            reply = reply.replace("$ABILITYNAME$", DiceHelper.getFateAbilityName(ability, abilityHighName, abilityLowName));
            reply = reply.replace("$ABILITYNAMEOPPONENT$", DiceHelper.getFateAbilityName(abilityop, abilityHighName, abilityLowName));
            reply = reply.replace("$EMOJIS$", DiceHelper.getFateEmojis(rolls));
            reply = reply.replace("$EMOJISOPPONENT$", DiceHelper.getFateEmojis(rollsop));
            reply = reply.replace("$OUTCOME$", String.valueOf(outcome));
            reply = reply.replace("$OUTCOMENAME$", DiceHelper.getFateOutcomeName(outcome, outcomeHighName));
            reply = reply.replace("+-", "-");

            Helper.sendMessage(textEvent, clientController, reply, false);
        } catch (Exception ex){
            throw ex;
        }
    }

    private int[] rollFates(){
        int[] rolls = new int[5];
        for(int i = 0; i < rolls.length -1; i++){
            rolls[i] = DiceHelper.getRandomNumber(-1, 1);
            rolls[4] += rolls[i];
        }

        return rolls;
    }
}