package AllDice.Commands;

import AllDice.Client;
import AllDice.Helper.Helper;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.ArrayList;

public class Help extends Command {
    public static String matchPattern = "^!help(:?((:?( )?)[0-9]+)?)(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.matches(matchPattern);
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try{
            String reply = "Alldice-Helppage:\n";
            ArrayList<String> values = Helper.getRegexMatches(textEvent.getMessage().toLowerCase(), "\\d+");

            if (values.size() == 0){
                for (CommandDef command : Commands.commands) {
                    reply += command.index + " -\t" + command.name + "\t" + command.syntax + "\n";
                }
                Helper.sendMessage(textEvent, client, reply, true, false);
            } else {
                boolean commandFound = false;
                int inputNumber = Integer.parseInt(values.get(0));

                for (CommandDef command : Commands.commands){
                    if (inputNumber == command.index){
                        reply += "Command: " + command.name + "\n";
                        reply += "Syntax: " + command.syntax + "\n";
                        reply += command.description + "\n";
                        reply += "Example: " + command.example + "\n";
                        commandFound = true;
                    }
                }

                if (commandFound){
                    Helper.sendMessage(textEvent, client, reply, true, false);
                } else {
                    Helper.sendMessage(textEvent, client, reply + "Couldnt find a command with that index...", true, false);
                }
            }
        } catch (Exception ex){
            Helper.sendMessage(textEvent, client, "An error has occurred...\nPlease try again with different inputs", true, false);
            System.out.println("Error in Help with input: " + textEvent.getMessage() + "\n\n" + ex);
        }
    }
}
