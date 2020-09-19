package AllDice.Helper;

import AllDice.Classes.Implementations.Tuple;

import java.util.Random;

public class DiceHelper {
    private static Random random = new Random();
    public static int swPass = 4;

    public static int getRandomNumber(int maxValue) {
        return random.nextInt(maxValue) +1;
    }

    public static int getRandomNumber(int minValue, int maxValue) {
        return random.nextInt((maxValue - minValue) + 1) + minValue;
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

    public static String getFateAbilityName(int ability, String abilityHighName, String abilityLowName){
        switch (ability)
        {
            case 8:
                return "Legendär";
            case 7:
                return "Episch";
            case 6:
                return "Fantastisch";
            case 5:
                return "Hervorragend";
            case 4:
                return "Großartig";
            case 3:
                return "Gut";
            case 2:
                return "Ordentlich";
            case 1:
                return "Durchschnittlich";
            case 0:
                return "Mäßig";
            case -1:
                return "Schwach";
            case -2:
                return "Fürchterlich";
        }

        if (ability > 8){
            return abilityHighName;
        } else {
            return abilityLowName;
        }
    }

    public static String getFateOutcomeName(int outcome, String outcomeHighName){
        switch (outcome)
        {
            case 4:
                return "⏫ Voller Erfolg";
            case 3:
                return "🔼 Erfolg";
            case 2:
                return "🔼 Erfolg";
            case 1:
                return "⏸️ Gleichstand";
            case 0:
                return "🔽 Fehlschlag oder Erfolg mit Haken";
        }

        if (outcome > 4) {
            return "♾ ️" + outcomeHighName;
        } else {
            return "⏬ Fehlschlag";
        }
    }

    public static String getFateEmojis(int[] rolls){
        String result = "";
        for(int i = 0; i < rolls.length -1; i++){
            switch (rolls[i]){
                case -1:
                    result += "🔽";
                    break;
                case 0:
                    result += "⏺️";
                    break;
                case 1:
                    result += "🔼";
                    break;
                default:
                    result += "SHIT";
            }
        }
        return result;
    }
}
