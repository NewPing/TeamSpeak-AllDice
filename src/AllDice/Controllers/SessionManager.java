package AllDice.Controllers;

import AllDice.Classes.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SessionManager {
    private static List<SessionController> sessionControllers = null;
    private static Timer timerCheckConfigs = null;
    private static TimerTask timerTaskCheckConfigs = null;

    public static void init() {
        sessionControllers = new ArrayList<>();

        checkSessions();
        //timerCheckConfigs = new Timer();
        //timerTaskCheckConfigs = new TimerTaskCheckConfigs();
        //timerCheckConfigs.scheduleAtFixedRate(timerTaskCheckConfigs, 1000, 30000);
    }

    public static void checkSessions() {
        new Thread(() -> SessionManager.invokeCreateNewSessionInstance()).start();
    }

    public static void invokeCreateNewSessionInstance() {
        try{
            sessionControllers.add(new SessionController());
        } catch (Exception ex) {
            Logger.log.severe("Exception in invokeCreateNewSessionInstance... " + ex);
        }
    }

    static class TimerTaskCheckConfigs extends TimerTask {
        @Override
        public void run() {
            SessionManager.checkSessions();
        }
    }
}
