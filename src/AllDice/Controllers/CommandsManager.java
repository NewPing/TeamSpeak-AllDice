package AllDice.Controllers;
import AllDice.Commands.*;
import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import AllDice.Models.CommandDef;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.ArrayList;

public class CommandsManager {
    private ClientController clientController = null;
    public ArrayList<CommandDef> commands = null;
    public int commandIndex = 1;

    public CommandsManager(ClientController _clientController){
        clientController = _clientController;
        commands = new ArrayList<CommandDef>() {
            {
                add(new CommandDef(
                        commandIndex++,
                        "info",
                        "!info",
                        "prints a info page",
                        "!info",
                        new Info(),
                        true,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "help",
                        "!help (index)",
                        "prints the help page",
                        "!help",
                        new Help(),
                        true,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "color",
                        "!color (color)",
                        "set the color of your output",
                        "!color (color)",
                        new Color(),
                        true,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "pm",
                        "!pm",
                        "opens a private chat with AllDice",
                        "!pm",
                        new PM(),
                        true,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "follow",
                        "!follow",
                        "lets alldice follow you",
                        "!follow",
                        new Follow(),
                        true,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "leave",
                        "!leave",
                        "disconnects alldice",
                        "!leave",
                        new Leave(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Probewurf",
                        "!(zahl)w(zahl)(+/-zahl)",
                        "Würfelt einen virtuellen Würfel mit der angegebenen Augenzahl",
                        "!2w6+1",
                        new W(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Savage Worlds Wildcard",
                        "!sww(zahl)(+/-zahl)",
                        "Savage Worlds Wildcard-Eigenschaftsprobe",
                        "!sww8+1",
                        new SWW(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Savage Worlds Statist",
                        "!sws(zahl)(+/-zahl)",
                        "Savage Worlds Statist Probe",
                        "!sws8+1",
                        new SWS(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Savage Worlds Damage",
                        "!swd(zahl),(zahl),(+/-zahl)",
                        "Savage Worlds Damage Probe",
                        "!swd5,4,+1",
                        new SWD(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Savage Worlds Damage-Zone",
                        "!swh",
                        "Savage Worlds Damage-Zone Probe",
                        "!swh",
                        new SWH(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Das Schwarze Auge",
                        "!dsa (zahl),(zahl),(zahl)(,+/-zahl)",
                        "Das Schwarze Auge Probewurf",
                        "!dsa 10,10,10,2)",
                        new DSA(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Cthulhu Probe",
                        "!cth(zahl)(+/-zahl)",
                        "Würfelt einen Cthulhu Probewurf",
                        "!cth50+2",
                        new CTH(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Dimensions Probe",
                        "!d(+/-zahl)",
                        "Würfelt einen W30 mit Modifikator",
                        "!d+5",
                        new DIM(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Fate Probe",
                        "!f(skill)(+/-)(mod),(goal)/(f(skill2)(+/-)(mod2))",
                        "Würfelt einen Fate Probewurf",
                        "!f5+2,3",
                        new Fate(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Fate Config",
                        "!fconfig (option) (new output)",
                        "Ermöglicht das setzen der Ausgabetexte von abilityHigh, abilityLow und outcomeHigh",
                        "!fconfig outcome Unbeschreibbar",
                        new FateConfig(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Star Trek Probe",
                        "!st(zahl),(zahl),(zahl)",
                        "Star Trek Probewurf mit: Anzahl W20, Schwellwert, Schwierigkeit",
                        "!st3,7,3",
                        new ST(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Star Trek Fokus",
                        "!stf(zahl),(zahl),(zahl)",
                        "Star Trek Fokus Probe mit: Anzahl W20, Schwellwert, Schwierigkeit",
                        "!stf3,7,3",
                        new STF(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "Star Trek Challenge",
                        "!stc(zahl)",
                        "Star Trek Challenge mit: Anzahl W6",
                        "!stc5",
                        new STC(),
                        false,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "adminhelp",
                        "!adminhelp (index)",
                        "prints the admin help page",
                        "!adminhelp",
                        new AdminHelp(),
                        true,
                        false
                ));
                add(new CommandDef(
                        commandIndex++,
                        "forceLeave",
                        "!forceLeave",
                        "Forces all AllDice Instances in the given text channel to leave",
                        "!forceLeave",
                        new ForceLeave(),
                        true,
                        true
                ));
                add(new CommandDef(
                        commandIndex++,
                        "update",
                        "!update",
                        "Not yet implemented! - Checks and performs a update of AllDice (if a newer version exists)",
                        "!update",
                        new Update(),
                        true,
                        true
                ));
                add(new CommandDef(
                        commandIndex++,
                        "uploadLogs",
                        "!uploadLogs",
                        "uploads all log files to standard channel (defined in settings)",
                        "!uploadLogs",
                        new UploadLogs(),
                        true,
                        true
                ));
                add(new CommandDef(
                        commandIndex++,
                        "deleteLogs",
                        "!deleteLogs",
                        "deletes all historical log files at the local disc",
                        "!deleteLogs",
                        new DeleteLogs(),
                        true,
                        true
                ));
                add(new CommandDef(
                        commandIndex++,
                        "reboot",
                        "!reboot",
                        "Shuts down all AllDice instances and then reboots",
                        "!reboot",
                        new Reboot(),
                        true,
                        true
                ));
                add(new CommandDef(
                        commandIndex++,
                        "shutdown",
                        "!shutdown",
                        "Shuts down AllDice and all of its instances",
                        "!shutdown",
                        new Shutdown(),
                        true,
                        true
                ));
            }
        };
    }

    public void handleCommandInput(TextMessageEvent messageEvent, ClientController clientController){
        for( int i = 0; i < commands.size(); i++){
            if (commands.get(i).command.check(messageEvent.getMessage().toLowerCase())){
                if (clientController.followClientID != -1 || commands.get(i).ignoreFollowFlag) {
                    Logger.log.fine("handleCommand :: User: " + messageEvent.getInvokerName() + " - Input: " + messageEvent.getMessage());
                    if (commands.get(i).requiresAllDiceAdminGroup) {
                        if (Helper.isUserAllDiceAdmin(messageEvent, clientController)){
                            commands.get(i).command.execute(messageEvent, clientController);
                        } else {
                            Helper.sendMessage(messageEvent, clientController, "Command execution denied ...", false);
                        }
                    } else {
                        commands.get(i).command.execute(messageEvent, clientController);
                    }
                }
            }
        }
    }
}
