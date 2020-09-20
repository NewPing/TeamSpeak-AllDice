package AllDice.Commands;

import AllDice.Classes.Logger;
import AllDice.Controllers.Client;
import AllDice.Helper.FileIO;
import AllDice.Helper.Helper;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.FileListEntry;

import java.io.*;
import java.util.List;

public class DeleteLogs extends Command {
    public static String matchPattern = "^!deleteLogs(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try {
            int successfulDeletions = 0;
            File[] logs = FileIO.getFiles("logs\\", ".log");
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
            Helper.sendMessage(textEvent, client, "Done - Deleting " + successfulDeletions + "/" + (logs.length) + " Logs", false);
        } catch (Exception ex){
            Logger.log.severe("Error in DeleteLogs.execute: " + ex);
        }
    }
}