package AllDice.Controllers;

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

    public Controller() {
        int errors = 0;
        errors += loadSettings();
        errors += loadClientNicknames();
        errors += loadUserColors();
        errors += loadUserConfigs();

        if (errors == 0){
            if (!Helper.isNullOrWhitespace(settings.ip) && !Helper.isNullOrWhitespace(settings.username) && !Helper.isNullOrWhitespace(settings.password)){
                try {
                    Helper.log("Starting ...");
                    clients.add(new Client(this, settings.ip, settings.username, settings.password));
                } catch (Exception ex) {
                    Helper.log("Exception in Controller... " + ex);
                    clients.clear();
                }

                Timer timerStayAlive = new Timer();
                TimerTask checkStayAlive = new CheckStayAlive(this);
                timerStayAlive.scheduleAtFixedRate(checkStayAlive, 1000, 30000);
            } else {
                Helper.log("Server IP, Query Username or Password not set...");
            }
        } else {
            Helper.log("Errors detected while startup...\nstopped start precedure...");
        }
    }

    private int loadSettings(){
        try{
            settings = FileIO.deserializeFromFile("settings.json", Settings.class);
            if (settings == null) {
                FileIO.serializeToFile("settings.json", new Settings());

                Helper.log("Couldnt find file '" + FileIO.getFilePath("settings.json") + "' or all needed parameters" +
                        "\n-> created it... please open the file and check the settings");
                return 1;
            }
        } catch (Exception ex){
            Helper.log("Exception in: loadSettings ... \n" + ex);
            return 1;
        }
        return 0;
    }

    private int loadClientNicknames(){
        try{
            Helper.possibleClientNicknames = (ArrayList<String>)(FileIO.deserializeFromFile("nicknames.json", ArrayList.class));
            if (Helper.possibleClientNicknames == null) {
                Helper.possibleClientNicknames = new ArrayList<>();
                Helper.possibleClientNicknames.add("AllDice");
                Helper.possibleClientNicknames.add("AllDice2");
                Helper.possibleClientNicknames.add("AllDice3");
                FileIO.serializeToFile("nicknames.json", Helper.possibleClientNicknames);

                Helper.log("Couldnt find file '" + FileIO.getFilePath("nicknames.json") + "' or all needed parameters" +
                        "\n-> created it... please open the file and check the nicknames");
                return 1;
            }
        } catch (Exception ex){
            Helper.log("Exception in: loadClientNicknames ... \n" + ex);
            return 1;
        }
        return 0;
    }

    private int loadUserColors(){
        try{
            Helper.userColor = FileIO.deserializeFromFile("usercolors.json", JDictionary.class);
            if (Helper.userColor == null) {
                Helper.userColor = new JDictionary<>();
            }
        } catch (Exception ex){
            Helper.log("Exception in: loadUserColor: couldnt load usercolors.json... continueing anyway...");
        }
        return 0;
    }

    private int loadUserConfigs(){
        try{
            Helper.userConfigs = FileIO.deserializeFromFile("userconfigs.json", UserConfigs.class);
            if (Helper.userConfigs == null) {
                Helper.userConfigs = new UserConfigs();
            }
        } catch (Exception ex){
            Helper.log("Exception in: loadUserFateConfig: couldnt load userconfigs.json... continueing anyway...");
        }
        return 0;
    }

    public void invokeCreateNewClientInstance() {
        try{
            if (settings.debug == 0){
                clients.add(new Client(this, settings.ip, settings.username, settings.password));
            }
        } catch (Exception ex) {
            Helper.log("Exception in invokeCreateNewClientInstance... " + ex);
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
                Helper.log("Running keep alive procedure...");
                if (settings.debug == 0){
                    new Thread(() -> controller.invokeCreateNewClientInstance()).start();
                }
            }
        }
    }
}
