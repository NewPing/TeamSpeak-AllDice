package AllDice.Helper;

import AllDice.Client;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    private static Random random = new Random();
    public static int commandIndexCounter = 1;
    public static int swPass = 4;
    public static JDictionary<String> userColor = new JDictionary<>();

    public static void sendMessage(TextMessageEvent textEvent, Client client, String message, boolean ignoreFollowFlag, Boolean forcePrivate) {
        if (client.followClientID != -1 || ignoreFollowFlag) {
            System.out.println("Output: " + message);
            if (forcePrivate) {
                client.api.sendTextMessage(TextMessageTargetMode.CLIENT, textEvent.getInvokerId(), "\n" + getUserColor(textEvent.getInvokerUniqueId()) + message);
            } else {
                client.api.sendTextMessage(textEvent.getTargetMode(), textEvent.getInvokerId(), "\n" + getUserColor(textEvent.getInvokerUniqueId()) + message);
            }
        }
    }

    public static String getUserColor(String uniqueUserID) {
        String colorCode = "[color=$COLOR$] ";
        if (Helper.userColor.contains(uniqueUserID)) {
            return colorCode.replace("$COLOR$", Helper.userColor.get(uniqueUserID));
        } else {
            return "";
        }
    }

    public static int getRandomNumber(int maxValue) {
        return random.nextInt(maxValue) +1;
    }

    public static Tuple<Integer, String> getExplodingDice(int diceSides) {
        int resNumber = 0;
        String resStr = "";
        int tmpNumber = 0;

        do
        {
            tmpNumber = getRandomNumber(diceSides);
            resNumber += tmpNumber;
            resStr += tmpNumber + "+";
        } while (tmpNumber == diceSides);

        resStr = resStr.substring(0, resStr.length() -1);
        return new Tuple<>(resNumber, resStr);
    }

    public static String getSWResultOutput(int number) {
        if (number < swPass)
        {
            return "Fehlschlag!";
        } else if (number < swPass * 2)
        {
            return "Erfolg!";
        } else
        {
            return "Erfolg - Steigerung um " + ((number / swPass) -1) + "!";
        }
    }

    public static String[] getSWHZoneOutput(int number, int number2) {
        String[] ret = new String[2];
        ret[0] = "";
        ret[1] = "";
        if (number == 2)
        {
            ret[0] = "Geschlechtsteile";
        } else if (number < 5)
        {
            if (getRandomNumber(2) == 1)
            {
                ret[0] = "Linker Arm";
            } else
            {
                ret[0] = "Rechter Arm";
            }
        } else if (number < 10)
        {
            ret[0] = "Eingeweide";
            if (number2 < 3)
            {
                ret[1] = "Gebrochen";
            }
            else if (number2 < 5)
            {
                ret[1] = "Zerschmettert";
            }
            else
            {
                ret[1] = "Ruiniert";
            }
        } else if (number == 10)
        {
            if (getRandomNumber(2) == 1)
            {
                ret[0] = "Linkes Bein";
            }
            else
            {
                ret[0] = "Rechtes Bein";
            }
        } else
        {
            ret[0] = "Kopf";
            if (number2 < 3)
            {
                ret[1] = "Scheußliche Narbe";
            }
            else if (number2 < 5)
            {
                ret[1] = "Geblendet";
            }
            else
            {
                ret[1] = "Gehirnerschütterung";
            }
        }
        return ret;
    }

    public static String getRandomNickname(ArrayList<String> alreadyUsedNicknames)
    {
        List<String> possibleNames = new ArrayList<>(possibleClientNicknames);
        possibleNames.removeAll(alreadyUsedNicknames);

        if (possibleNames.size() > 0)
        {
            if (possibleNames.size() == 1){
                return possibleNames.get(0);
            } else {
                return possibleNames.get(random.nextInt(possibleNames.size() -1));
            }
        } else {
            return "AllDice";
        }
    }

    public static ArrayList<String> getRegexMatches(String str, String pattern) {
        ArrayList<String> matches = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern).matcher(str);
        while (m.find()) {
            matches.add(m.group());
        }
        return matches;
    }

    public static boolean isNullOrWhitespace(String s) {
        return s == null || isWhitespace(s) || s.equals("");

    }

    private static boolean isWhitespace(String s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static String blanc_w_Output =
            "--$AUTHOR$--\n" +
                    "Rechnung: $RANDNUMBER$ = $SUM$\nSumme: $SUM$+$ADD$\nErgebnis = $RESULT$";
    public static String blanc_sww_Output =
            "--$AUTHOR$--\n" +
                    "Probewurf W$INPUTNUMBER$\nRechnung: $RANDNUMBERS0$\nSumme: $SUM0$+$ADD$ = $RESULT0$\n$OUTPUT0$\n\n" +
                    "Wildcardwurf: W6\nRechnung: $RANDNUMBERS1$\nSumme: $SUM1$+$ADD$ = $RESULT1$\n$OUTPUT1$";
    public static String blanc_sws_Output =
            "--$AUTHOR$--\n" +
                    "Probewurf W$INPUTNUMBER$\nRechnung: $RANDNUMBERS0$\nSumme: $SUM0$+$ADD$ = $RESULT0$\n$OUTPUT0$";
    public static String blanc_swd_Output =
            "--$AUTHOR$--\n" +
                    "Schadenswurf W$INPUTNUMBER0$\nRechnung: $RANDNUMBERS0$\nSumme: $SUM0$\n\n" +
                    "Schadenswurf W$INPUTNUMBER1$\nRechnung: $RANDNUMBERS1$\nSumme: $SUM1$\n\n" +
                    "Ergebnis: $SUM0$ + $SUM1$ +$ADD$ = $RESULT$";
    public static String blanc_swh_Output =
            "--$AUTHOR$--\n" +
                    "Trefferzonenwurf W6: $RANDNUMBER0$\n" +
                    "Trefferzonenwurf W6: $RANDNUMBER1$\n" +
                    "Ergebnis: $SUM$\n\n" +
                    "Trefferzone: $ZONE0$" +
                    "\nZusatzwurf W6: $RANDNUMBER2$ - $ZONE1$";
    public static String blanc_dsa_Output =
            "--$AUTHOR$--\n" +
                    "Ergebnis: $RANDNUMBER0$, $RANDNUMBER1$, $RANDNUMBER2$ => $RESULT$ = $RESSUM$";
    public static String blanc_cth_Output =
            "--$AUTHOR$--\n" +
                    "Ergebnis: $RANDNUMBER$ / ( $INPUTNUMBERS0$ +$INPUTNUMBERS1$ ) => $RESULT$";

    public static List<String> possibleClientNicknames = new ArrayList<>();
}
