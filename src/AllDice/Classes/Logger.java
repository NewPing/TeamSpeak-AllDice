package AllDice.Classes;

import AllDice.Helper.FileIO;
import AllDice.Models.Settings;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logger {
    public static Settings settings = null;
    public static String logFolder = "logs/";
    public static String standardLogfile = "alldice";
    public static String logfileExtension = ".log";
    public final static java.util.logging.Logger log = java.util.logging.Logger.getLogger(java.util.logging.Logger.getGlobal().getName());

    public static void init() throws Exception {
        try{
            openNewLogfile();

            log.setLevel(Level.FINEST);

            for(int i = 0; i < log.getHandlers().length; i++){
                log.removeHandler(log.getHandlers()[i]);
            }

            if (settings.writeLog){
                FileHandler fileHandler = new FileHandler(logFolder + standardLogfile + logfileExtension);
                fileHandler.setFormatter(new SimpleFormatter());
                fileHandler.setLevel(Level.ALL);
                log.addHandler(fileHandler);
            }

            log.info("Logger initialized...");
        } catch (Exception ex){
            System.out.println("Error in Logger.init: " + ex);
            throw ex;
        }
    }

    public static void openNewLogfile() throws IOException {
        if (settings != null && settings.writeLog){
            if (FileIO.exists(logFolder) == false){
                FileIO.createDirectory(logFolder);
            }
            if (FileIO.exists(logFolder + standardLogfile + logfileExtension)){
                FileIO.fileMove(logFolder + standardLogfile + logfileExtension, logFolder + standardLogfile + "_" + getTimestemp() + logfileExtension);
            }
        }
    }

    public static String getTimestemp(){
        return new Timestamp(System.currentTimeMillis()).toString().replace("-", "").replace(":", "").replace(" ", "-").split("\\.")[0];
    }
}
