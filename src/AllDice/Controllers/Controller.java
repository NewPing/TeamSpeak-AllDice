package AllDice.Controllers;

import AllDice.Classes.Implementations.JDictionary;
import AllDice.Classes.Logger;
import AllDice.Helper.*;
import AllDice.Models.Settings;
import AllDice.Models.UserConfigs;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    public static ArrayList<Client> clients = new ArrayList<>();
    public static Settings settings;
    public static final String Version = "1.2";
    private static boolean clientsCrashed = false;

    public Controller(){
        int errors = 0;
        errors += loadSettings();
        Logger.settings = settings;
        Logger.openNewLogfile();

        errors += loadClientNicknames();
        errors += loadUserColors();
        errors += loadUserConfigs();

        if (errors == 0){
            if (!Helper.isNullOrWhitespace(settings.ip) && !Helper.isNullOrWhitespace(settings.username) && !Helper.isNullOrWhitespace(settings.password)){
                try {
                    Logger.log("Starting ...");
                    clients.add(new Client(this, settings.ip, settings.username, settings.password));
                } catch (Exception ex) {
                    Logger.log("Exception in Controller... " + ex);
                    clients.clear();
                }

                Timer timerStayAlive = new Timer();
                TimerTask checkStayAlive = new CheckStayAlive(this);
                timerStayAlive.scheduleAtFixedRate(checkStayAlive, 1000, 30000);
            } else {
                Logger.log("Server IP, Query Username or Password not set...");
            }
        } else {
            Logger.log("Errors detected while startup...\nstopped start precedure...");
            System.exit(-1);
        }
    }

    private int loadSettings(){
        try{
            Logger.log("loading settings...");
            settings = FileIO.deserializeFromFile("settings.json", Settings.class);
            if (settings == null) {
                FileIO.serializeToFile("settings.json", new Settings());

                Logger.log("Couldnt find file '" + FileIO.getFilePath("settings.json") + "' or all needed parameters" +
                        "\n-> created it... please open the file and check the settings");
                return 1;
            }
        } catch (Exception ex){
            Logger.log("Exception in: loadSettings ... \n" + ex);
            return 1;
        }
        return 0;
    }

    private int loadClientNicknames(){
        try{
            Logger.log("loading client nicknames...");
            Helper.possibleClientNicknames = (ArrayList<String>)(FileIO.deserializeFromFile("nicknames.json", ArrayList.class));
            if (Helper.possibleClientNicknames == null) {
                Helper.possibleClientNicknames = new ArrayList<>();
                Helper.possibleClientNicknames.add("AllDice");
                Helper.possibleClientNicknames.add("AllDice2");
                Helper.possibleClientNicknames.add("AllDice3");
                FileIO.serializeToFile("nicknames.json", Helper.possibleClientNicknames);

                Logger.log("Couldnt find file '" + FileIO.getFilePath("nicknames.json") + "' or all needed parameters" +
                        "\n-> created it... please open the file and check the nicknames if you want to set custom nicknames for different alldice instances" +
                        "\n--> proceeding anyway...");
                return 0;
            }
        } catch (Exception ex){
            Logger.log("Exception in: loadClientNicknames ... \n" + ex);
            return 1;
        }
        return 0;
    }

    private int loadUserColors(){
        try{
            Logger.log("loading user colors...");
            Helper.userColor = FileIO.deserializeFromFile("usercolors.json", JDictionary.class);
            if (Helper.userColor == null) {
                Helper.userColor = new JDictionary<>();
            }
        } catch (Exception ex){
            Logger.log("Exception in: loadUserColor: couldnt load usercolors.json... continueing anyway...");
        }
        return 0;
    }

    private int loadUserConfigs(){
        try{
            Logger.log("loading user configs...");
            Helper.userConfigs = FileIO.deserializeFromFile("userconfigs.json", UserConfigs.class);
            if (Helper.userConfigs == null) {
                Helper.userConfigs = new UserConfigs();
            }
        } catch (Exception ex){
            Logger.log("Exception in: loadUserFateConfig: couldnt load userconfigs.json... continueing anyway...");
        }
        return 0;
    }

    public void invokeCreateNewClientInstance() {
        try{
            if (settings.startInServerMode || clientsCrashed){
                clientsCrashed = false;
                clients.add(new Client(this, settings.ip, settings.username, settings.password));
            }
        } catch (Exception ex) {
            Logger.log("Exception in invokeCreateNewClientInstance... " + ex);
        }
    }

    public void clientLeave(int clientID){
        for(int i = 0; i < clients.size(); i++){
            if (clients.get(i).clientID == clientID){
                clients.remove(clients.get(i));
            }
        }
    }

    class CheckStayAlive extends TimerTask {

        private Controller controller = null;

        public CheckStayAlive(Controller _controller) {
            controller = _controller;
        }

        @Override
        public void run() {
            if (controller.clients.size() == 0) {
                if (settings.startInServerMode){
                    Logger.log("Running keep alive procedure... (clients.size is 0)");
                    new Thread(() -> controller.invokeCreateNewClientInstance()).start();
                } else {
                    Logger.log("Would like to run keep alive procedure... (clients.size is 0) but startInServerMode is set to false");
                }
            } else {
                //check if one or more clients lost the connection to the server / crashed
                for(int i = 0; i < controller.clients.size(); i++){
                    if (!controller.clients.get(i).query.isConnected()){
                        Logger.log("Client: " + controller.clients.get(i).clientID + " connected? " + controller.clients.get(i).query.isConnected());
                        controller.clientsCrashed = true;
                    }
                }
                if (clientsCrashed){
                    //shit went south - now we need to clean that mess up...
                    Logger.log("Running keep alive crash procedure (cleanup clients & start new client instance | clientsCrashed is true)...");
                    controller.clients = new ArrayList<>();
                    controller.invokeCreateNewClientInstance();
                }
            }
        }
    }
}
