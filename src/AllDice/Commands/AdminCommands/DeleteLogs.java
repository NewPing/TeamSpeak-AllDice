package AllDice.Commands.AdminCommands;

import AllDice.Classes.Logger;
import AllDice.Controllers.ClientController;
import AllDice.Helper.FileIO;
import AllDice.Helper.Helper;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.io.*;

public class DeleteLogs extends Command {
    public static String matchPattern = "^!deleteLogs(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, ClientController clientController) {
        try {
            if (clientController.sessionController.settings.writeLog){
                int successfulDeletions = 0;
                File[] logs = null;
                if (FileIO.exists("logs\\")){
                    logs = FileIO.getFiles("logs\\", ".log");
                }
                for(int i = 0; i < logs.length; i++) {
                    try {
                        if(logs[i].getName() != Logger.standardLogfile + Logger.logfileExtension) {
                            logs[i].delete();
                            successfulDeletions++;
                        }
                    } catch (Exception ex){
                        Logger.log.warning("Error in DeleteLogs.execute (delete logs) - Failed to delete log: " + logs[i].getAbsolutePath());
                    }
                }
                Helper.sendMessage(textEvent, clientController, "Done - Deleting " + successfulDeletions + "/" + (logs.length) + " Logs", false);
            } else {
                Helper.sendMessage(textEvent, clientController, "Option: writeLogs is disabled in the settings file...\n-> DeleteLogs is also disabled", false);
            }
        } catch (Exception ex){
            Logger.log.severe("Error in DeleteLogs.execute: " + ex);
        }
    }
}