package AllDice.Helper;

import AllDice.Classes.Implementations.JDictionary;
import AllDice.Classes.Logger;
import AllDice.Controllers.ClientController;
import AllDice.Models.UserConfigs;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    private static Random random = new Random();

    public static JDictionary<String> userColor = new JDictionary<>();
    public static UserConfigs userConfigs = new UserConfigs();
    public static List<String> possibleClientNicknames = new ArrayList<>();

    public static void sendMessage(TextMessageEvent textEvent, ClientController clientController, String message, Boolean forcePrivate) {
        Logger.log.fine(clientController.clientID + ":: Output: " + message);
        if (forcePrivate) {
            clientController.api.sendTextMessage(TextMessageTargetMode.CLIENT, textEvent.getInvokerId(), "\n" + getUserColor(textEvent.getInvokerUniqueId()) + message);
        } else {
            clientController.api.sendTextMessage(textEvent.getTargetMode(), textEvent.getInvokerId(), "\n" + getUserColor(textEvent.getInvokerUniqueId()) + message);
        }
    }

    public static void sendInfoMessage(ClientController clientController, String message, Boolean forcePrivate) {
        Logger.log.fine(clientController.clientID + ":: Output: " + message);
        if (forcePrivate){
            clientController.api.sendTextMessage(TextMessageTargetMode.CLIENT, clientController.followClientID, message);
        } else {
            clientController.api.sendTextMessage(TextMessageTargetMode.CHANNEL, clientController.api.getClientInfo(clientController.clientID).getChannelId(), message);
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

    public static UserConfigs.UserConfig getUserConfig(String uniqueUserID) {
        if (Helper.userConfigs.userConfigs.contains(uniqueUserID)) {
            return Helper.userConfigs.userConfigs.get(uniqueUserID);
        } else {
            return null;
        }
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

    public static boolean isUserAllDiceAdmin(TextMessageEvent textEvent, ClientController _clientController) {
        if (Helper.isNullOrWhitespace(_clientController.sessionController.settings.adminGroupName) == false){
            com.github.theholywaffle.teamspeak3.api.wrapper.Client client = getClientByUniqueId(textEvent.getInvokerUniqueId(), _clientController);
            List<ServerGroup> clientGroups = _clientController.api.getServerGroupsByClientId(client.getDatabaseId());
            for (int i = 0; i < clientGroups.size(); i++){
                if (clientGroups.get(i).getName().toLowerCase().equals(_clientController.sessionController.settings.adminGroupName.toLowerCase())){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static com.github.theholywaffle.teamspeak3.api.wrapper.Client getClientByUniqueId(String uniqueIdentifier, ClientController clientController){
        List<com.github.theholywaffle.teamspeak3.api.wrapper.Client> clients = clientController.api.getClients();
        for (int i = 0; i < clients.size(); i++){
            if (clients.get(i).getUniqueIdentifier().equals(uniqueIdentifier)){
                return clients.get(i);
            }
        }
        return null;
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
}
