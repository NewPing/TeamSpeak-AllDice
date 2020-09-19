package AllDice.Controllers;

import AllDice.Helper.Commands;
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

public class Client {
    public Controller controller;
    public int clientID = -1;
    public int currentChannelID = -1;
    public int followClientID = -1;
    public String followClientUniqueID = "-1";
    public TS3Api api = null;
    public TS3Query query = null;
    public Commands commands = null;

    /**
     * Constructor
     * @param _controller
     * @param ip
     * @param username
     * @param password
     */
    public Client(Controller _controller, String ip, String username, String password){
        try{
            controller = _controller;
            TS3Config config = new TS3Config();

            config.setHost(ip);
            query = new TS3Query(config);
            query.connect();
            api = query.getApi();
            api.login(username,password);
            api.selectVirtualServerById(controller.settings.virtualServerID);

            clientID = api.whoAmI().getId();
            currentChannelID = api.whoAmI().getChannelId();

            setNickname(api);

            try{
                if (Helper.isNullOrWhitespace(controller.settings.standardChannelName) == false){
                    api.moveClient(clientID, api.getChannelsByName(controller.settings.standardChannelName).get(0).getId());
                }
            } catch (Exception ex) {
                Logger.log("Warning: Exception in client constructor... couldnt find specified standard channel name... remaining in standard server channel..." + ex);
            }

            commands = new Commands(this);
            initializeEvents(api, query, this);
            Logger.log("Client " + clientID + " started successfully!");
        } catch ( Exception ex){
            Logger.log("Exception in client constructor - Please check that the server is running and the login credentials are correct: \n" + ex);
            controller.clientLeave(clientID);
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
            Logger.log(e.toString());
        }
    }

    private void initializeEvents(TS3Api api, TS3Query query, Client clientInstance){
        api.registerAllEvents();
        api.addTS3Listeners(new TS3EventAdapter() {

            @Override
            public void onClientMoved(ClientMovedEvent e) {
                if (e.getClientId() == followClientID) {
                    api.moveClient(clientID, e.getTargetChannelId());
                }
            }

            @Override
            public void onClientLeave(ClientLeaveEvent e) {
                if (e.getClientId() == followClientID){
                    controller.clientLeave(clientID);
                    query.exit();
                }
            }

            @Override
            public void onTextMessage(TextMessageEvent e) {
                if ((e.getTargetMode() == TextMessageTargetMode.CHANNEL || e.getTargetMode() == TextMessageTargetMode.CLIENT) && e.getInvokerId() != clientID) {
                    if (e.getMessage().charAt(0) == '!') {
                        commands.handleCommandInput(e, clientInstance);
                    }
                }
            }
        });
    }
}
