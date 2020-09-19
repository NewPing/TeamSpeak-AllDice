package AllDice.Commands;

import AllDice.Classes.Logger;
import AllDice.Controllers.Client;
import AllDice.Helper.FileIO;
import AllDice.Helper.Helper;
import AllDice.Models.Command;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.io.*;

public class UploadLog extends Command {
    public static String matchPattern = "^!uploadLogs(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try {
            int successfulUploads = 0;
            File[] logs = FileIO.getFiles("logs\\", ".log");

            try{
                client.api.deleteFile("logs", client.standardChannelID);
                client.api.createFileDirectory("logs", client.standardChannelID);
            } catch (Exception ex){
                Logger.log.warning("Error in UploadLog.execute (create empty 'logs' folder in channel: " + client.controller.settings.standardChannelName + ")");
            }

            for(int i = 0; i < logs.length; i++){
                try {
                    client.api.uploadFile(new FileInputStream(logs[i]), logs[i].length(), "logs/" + logs[i].getName(), true, client.standardChannelID);
                    successfulUploads++;
                } catch (Exception ex){
                    Logger.log.warning("Error in UploadLog.execute (upload logs) - Failed to upload log: " + logs[i].getAbsolutePath());
                }
            }
            Helper.sendMessage(textEvent, client, "Done - Uploaded " + successfulUploads + "/" + logs.length + " Logs", false);
        } catch (Exception ex){
            Logger.log.severe("Error in UploadLog.execute: " + ex);
        }
    }
}