package AllDice.Controllers;

import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.*;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    public SessionController sessionController;
    public int clientID = -1;
    public int standardChannelID = -1;
    public boolean isActive = false;
    public int followClientID = -1;
    public String followClientUniqueID = "-1";
    public TS3Api api = null;
    public TS3Query query = null;
    public CommandsManager commandsManager = null;
    public boolean startedSuccessfully = false;

    /**
     * Constructor
     * @param _sessionController
     * @param ip
     * @param username
     * @param password
     */
    public ClientController(SessionController _sessionController, String ip, String username, String password){
        try{
            sessionController = _sessionController;
            TS3Config config = new TS3Config();

            config.setHost(ip);
            query = new TS3Query(config);
            query.connect();
            api = query.getApi();
            api.login(username,password);
            api.selectVirtualServerById(sessionController.settings.virtualServerID);

            clientID = api.whoAmI().getId();

            setNickname(api);

            boolean leftAfterMove = false;
            try{
                if (Helper.isNullOrWhitespace(sessionController.settings.standardChannelName) == false){
                    leftAfterMove = moveClient(clientID, api.getChannelsByName(sessionController.settings.standardChannelName).get(0).getId());
                }
            } catch (Exception ex) {
                Logger.log.info("Exception in client constructor... couldnt find specified standard channel name... remaining in standard server channel..." + ex);
            }
            if (!leftAfterMove){
                standardChannelID = api.getChannelsByName(sessionController.settings.standardChannelName).get(0).getId();
                commandsManager = new CommandsManager(this);
                initializeEvents(api, query, this);
                startedSuccessfully = true;
                Logger.log.fine("ClientController " + clientID + " started successfully!");
            }
        } catch ( Exception ex){
            Logger.log.severe("Exception in client constructor - Please check that the server is running and the login credentials are correct: \n" + ex);
            sessionController.clientLeave(clientID);
            query.exit();
        }
    }

    private void setNickname(TS3Api api){
        List<com.github.theholywaffle.teamspeak3.api.wrapper.Client> clients = api.getClients();
        ArrayList<String> alreadyUsedNicknames = new ArrayList<>();
        for(int i = 0; i < clients.size(); i++){
            if (clients.get(i).isServerQueryClient()){
                alreadyUsedNicknames.add(clients.get(i).getNickname());
            }
        }
        alreadyUsedNicknames.remove(api.getClientInfo(clientID).getNickname());
        String nickname = Helper.getRandomNickname(alreadyUsedNicknames);
        try{
            if (!api.whoAmI().getNickname().equals(nickname)){
                api.setNickname(nickname);
            }
        } catch (Exception e) {
            Logger.log.fine(e.toString());
        }
    }

    public boolean moveClient(int clientID, int targetChannelID){
        boolean leaveAfterFollow = false;
        api.moveClient(clientID, targetChannelID);
        for(int i = 0; i < sessionController.clientControllers.size(); i++){
            ClientController otherClient = sessionController.clientControllers.get(i);
            if (targetChannelID == otherClient.api.getClientInfo(otherClient.clientID).getChannelId()
                && this.clientID != otherClient.clientID
                    && this.isActive == otherClient.isActive){
                        leaveAfterFollow = true;
            }
        }

        if (leaveAfterFollow){
            Helper.sendInfoMessage(this, "Found other AllDice-Client in this channel... I'm leaving", this.isActive);
            sessionController.clientLeave(clientID);
            query.exit();
        }
        return leaveAfterFollow;
    }

    private void initializeEvents(TS3Api api, TS3Query query, ClientController clientControllerInstance){
        api.registerAllEvents();
        api.addTS3Listeners(new TS3EventAdapter() {

            @Override
            public void onClientMoved(ClientMovedEvent e) {
                if (e.getClientId() == followClientID) {
                    moveClient(clientID, e.getTargetChannelId());
                }
            }

            @Override
            public void onClientLeave(ClientLeaveEvent e) {
                if (e.getClientId() == followClientID){
                    sessionController.clientLeave(clientID);
                    query.exit();
                }
            }

            @Override
            public void onTextMessage(TextMessageEvent e) {
                if ((e.getTargetMode() == TextMessageTargetMode.CHANNEL || e.getTargetMode() == TextMessageTargetMode.CLIENT) && e.getInvokerId() != clientID) {
                    if (e.getMessage().charAt(0) == '!') {
                        commandsManager.handleCommandInput(e, clientControllerInstance);
                    }
                }
            }
        });
    }
}
