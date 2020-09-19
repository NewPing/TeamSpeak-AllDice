package AllDice.Commands;

import AllDice.Controllers.Client;
import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import AllDice.Models.Command;
import AllDice.Models.CommandDef;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.ArrayList;

public class Help extends Command {
    public static String matchPattern = "^!help(:?((:?( )?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        help(textEvent, client, false);
    }

    public static void help(TextMessageEvent textEvent, Client client, boolean isAdminHelp){
        try{
            String reply = "";
            if (isAdminHelp){
                reply = "Alldice-Admin-Helppage:\n";
            } else {
                reply = "Alldice-Helppage:\n";
            }

            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");

            if (values.size() == 0){
                for (CommandDef command : client.commands.commands) {
                    if (command.requiresAllDiceAdminGroup == isAdminHelp){
                        reply += command.index + " -\t" + command.name + "\t" + command.syntax + "\n";
                    }
                }
                Helper.sendMessage(textEvent, client, reply, false);
            } else {
                boolean commandFound = false;
                int inputNumber = Integer.parseInt(values.get(0));

                for (CommandDef command : client.commands.commands){
                    if (command.requiresAllDiceAdminGroup == isAdminHelp){
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
            Logger.log.severe("Error in Help with input: " + textEvent.getMessage() + "\n\n" + ex);
        }
    }
}
