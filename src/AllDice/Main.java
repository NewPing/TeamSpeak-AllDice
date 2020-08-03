package AllDice;

import AllDice.Controllers.Controller;
import AllDice.Helper.FileIO;

public class Main {

    private static Controller controller = null;

    public static void main(String[] args) {
        System.out.println("hello!");
        controller = new Controller();
    }
}