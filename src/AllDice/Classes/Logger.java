package AllDice.Classes;

import AllDice.Helper.FileIO;
import AllDice.Models.Settings;

import java.sql.Timestamp;

public class Logger {
    public static Settings settings = null;
    public static String logFolder = "logs\\";
    public static String standardLogfile = "alldice";
    public static String logfileExtension = ".log";

    public static void log(String text){
        System.out.println(text); //standard output

        if (settings != null && settings.writeLog){ //write log file
            FileIO.appendAllText(logFolder + standardLogfile + logfileExtension, text);
        }
    }

    public static void openNewLogfile(){
        if (settings != null && settings.writeLog){
            if (FileIO.exists(logFolder) == false){
                FileIO.createDirectory(logFolder);
            }
            if (FileIO.exists(logFolder + standardLogfile + logfileExtension)){
                FileIO.fileMove(logFolder + standardLogfile + logfileExtension, logFolder + standardLogfile + "_" + getTimestemp() + logfileExtension);
            }
            Logger.log("opened new logfile...");
        }
    }

    public static String getTimestemp(){
        return new Timestamp(System.currentTimeMillis()).toString().replace("-", "").replace(":", "").replace(" ", "-").split("\\.")[0];
    }
}
