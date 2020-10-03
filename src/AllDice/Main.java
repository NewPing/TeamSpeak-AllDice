package AllDice;

import AllDice.Controllers.SessionController;
import AllDice.Controllers.SessionManager;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> SessionManager.init()).start();
        while(true){
            Thread.sleep(1000);
        }
    }
}