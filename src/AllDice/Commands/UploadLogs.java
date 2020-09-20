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

public class UploadLogs extends Command {
    public static String matchPattern = "^!uploadLogs(?: +)?$";

    @Override
    public boolean check(String input) {
        return input.toLowerCase().matches(matchPattern.toLowerCase());
    }

    @Override
    public void execute(TextMessageEvent textEvent, Client client) {
        try {
            if (client.controller.settings.writeLog) {
                int successfulUploads = 0;
                File[] logs = null;
                if (FileIO.exists("logs\\")){
                    logs = FileIO.getFiles("logs\\", ".log");
                }

                try{
                    removeTs3LogsDirectory(client);
                    client.api.createFileDirectory("logs", client.standardChannelID);
                } catch (Exception ex){
                    Logger.log.warning("Error in UploadLogs.execute (create empty 'logs' folder in channel: " + client.controller.settings.standardChannelName + ")");
                }

                for(int i = 0; i < logs.length; i++){
                    try {
                        client.api.uploadFile(new FileInputStream(logs[i]), logs[i].length(), "logs/" + logs[i].getName(), true, client.standardChannelID);
                        successfulUploads++;
                    } catch (Exception ex){
                        Logger.log.warning("Error in UploadLogs.execute (upload logs) - Failed to upload log: " + logs[i].getAbsolutePath());
                    }
                }
                Helper.sendMessage(textEvent, client, "Done - Uploaded " + successfulUploads + "/" + logs.length + " Logs", false);
            } else {
                Helper.sendMessage(textEvent, client, "Option: writeLogs is disabled in the settings file...\n-> UploadLogs is also disabled", false);
            }
        } catch (Exception ex){
            Logger.log.severe("Error in UploadLogs.execute: " + ex);
        }
    }

    private void removeTs3LogsDirectory(Client client) {
        List<FileListEntry> files = client.api.getFileList("", client.standardChannelID);
        for(int i = 0; i < files.size(); i++){
            if (files.get(i).isDirectory() && files.get(i).getName().equals("logs")) {
                client.api.deleteFile("logs", client.standardChannelID);
            }
        }
    }
}