package AllDice.Commands;

import AllDice.Controllers.Client;
import AllDice.Controllers.Commands;
import AllDice.Helper.Helper;
import AllDice.Models.Command;
import AllDice.Models.CommandDef;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.ArrayList;

public class AdminHelp extends Command {
    public static String matchPattern = "^!adminhelp(:?((:?( )?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.matches(matchPattern);
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try{
            String reply = "Alldice-Admin-Helppage:\n";
            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");

            if (values.size() == 0){
                for (CommandDef command : Commands.commands) {
                    if (command.requiresAllDiceAdminGroup){
                        reply += command.index + " -\t" + command.name + "\t" + command.syntax + "\n";
                    }
                }
                Helper.sendMessage(textEvent, client, reply, false);
            } else {
                boolean commandFound = false;
                int inputNumber = Integer.parseInt(values.get(0));

                for (CommandDef command : Commands.commands){
                    if (command.requiresAllDiceAdminGroup){
                        if (inputNumber == command.index){
                            reply += "Command: " + command.name + "\n";
                            reply += "Syntax: " + command.syntax + "\n";
                            reply += command.description + "\n";
                            reply += "Example: " + command.example + "\n";
                            commandFound = true;
                        }
                    }
                }

                if (commandFound){
                    Helper.sendMessage(textEvent, client, reply, false);
                } else {
                    Helper.sendMessage(textEvent, client, reply + "Couldnt find a command with that index...", false);
                }
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, client, "An error has occurred...\nPlease try again with different inputs", false);
            Helper.log("Error in AdminHelp with input: " + textEvent.getMessage() + "\n\n" + ex);
        }
    }
}