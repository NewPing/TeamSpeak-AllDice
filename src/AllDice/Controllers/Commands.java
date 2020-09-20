package AllDice.Controllers;
import AllDice.Commands.*;
import AllDice.Helper.Helper;
import AllDice.Classes.Logger;
import AllDice.Models.CommandDef;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.ArrayList;

public class Commands {
    private Client client = null;
    public ArrayList<CommandDef> commands = null;

    public Commands(Client _client){
        client = _client;
        commands = new ArrayList<CommandDef>() {
            {
                add(new CommandDef(
                        "info",
                        "!info",
                        "prints a info page",
                        "!info",
                        new Info(),
                        true,
                        false
                ));
                add(new CommandDef(
                        "help",
                        "!help (index)",
                        "prints the help page",
                        "!help",
                        new Help(),
                        true,
                        false
                ));
                add(new CommandDef(
                        "color",
                        "!color (color)",
                        "set the color of your output",
                        "!color (color)",
                        new Color(),
                        true,
                        false
                ));
                add(new CommandDef(
                        "pm",
                        "!pm",
                        "opens a private chat with AllDice",
                        "!pm",
                        new PM(),
                        true,
                        false
                ));
                add(new CommandDef(
                        "follow",
                        "!follow",
                        "lets alldice follow you",
                        "!follow",
                        new Follow(),
                        true,
                        false
                ));
                add(new CommandDef(
                        "leave",
                        "!leave",
                        "disconnects alldice",
                        "!leave",
                        new Leave(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Probewurf",
                        "!(zahl)w(zahl)(+/-zahl)",
                        "Würfelt einen virtuellen Würfel mit der angegebenen Augenzahl",
                        "!2w6+1",
                        new W(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Savage Worlds Wildcard",
                        "!sww(zahl)(+/-zahl)",
                        "Savage Worlds Wildcard-Eigenschaftsprobe",
                        "!sww8+1",
                        new SWW(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Savage Worlds Statist",
                        "!sws(zahl)(+/-zahl)",
                        "Savage Worlds Statist Probe",
                        "!sws8+1",
                        new SWS(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Savage Worlds Damage",
                        "!swd(zahl),(zahl),(+/-zahl)",
                        "Savage Worlds Damage Probe",
                        "!swd5,4,+1",
                        new SWD(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Savage Worlds Damage-Zone",
                        "!swh",
                        "Savage Worlds Damage-Zone Probe",
                        "!swh",
                        new SWH(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Das Schwarze Auge",
                        "!dsa (zahl),(zahl),(zahl)(,+/-zahl)",
                        "Das Schwarze Auge Probewurf",
                        "!dsa 10,10,10,2)",
                        new DSA(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Cthulhu Probe",
                        "!cth(zahl)(+/-zahl)",
                        "Würfelt einen Cthulhu Probewurf",
                        "!cth50+2",
                        new CTH(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Dimensions Probe",
                        "!d(+/-zahl)",
                        "Würfelt einen W30 mit Modifikator",
                        "!d+5",
                        new DIM(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Fate Probe",
                        "!f(skill)(+/-)(mod),(goal)/(f(skill2)(+/-)(mod2))",
                        "Würfelt einen Fate Probewurf",
                        "!f5+2,3",
                        new Fate(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Fate Config",
                        "!fconfig (option) (new output)",
                        "Ermöglicht das setzen der Ausgabetexte von abilityHigh, abilityLow und outcomeHigh",
                        "!fconfig outcome Unbeschreibbar",
                        new FateConfig(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Star Trek Probe",
                        "!st(zahl),(zahl),(zahl)",
                        "Star Trek Probewurf mit: Anzahl W20, Schwellwert, Schwierigkeit",
                        "!st3,7,3",
                        new ST(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Star Trek Fokus",
                        "!stf(zahl),(zahl),(zahl)",
                        "Star Trek Fokus Probe mit: Anzahl W20, Schwellwert, Schwierigkeit",
                        "!stf3,7,3",
                        new STF(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "Star Trek Challenge",
                        "!stc(zahl)",
                        "Star Trek Challenge mit: Anzahl W6",
                        "!stc5",
                        new STC(),
                        false,
                        false
                ));
                add(new CommandDef(
                        "adminhelp",
                        "!adminhelp (index)",
                        "prints the admin help page",
                        "!adminhelp",
                        new AdminHelp(),
                        true,
                        true
                ));
                add(new CommandDef(
                        "update",
                        "!update",
                        "Checks and performs a update of AllDice (if a newer version exists)",
                        "!update",
                        new Update(),
                        true,
                        true
                ));
                add(new CommandDef(
                        "uploadLogs",
                        "!uploadLogs",
                        "uploads all log files to standard channel (defined in settings)",
                        "!uploadLogs",
                        new UploadLogs(),
                        true,
                        true
                ));
                add(new CommandDef(
                        "deleteLogs",
                        "!deleteLogs",
                        "deletes all historical log files at the local disc",
                        "!deleteLogs",
                        new DeleteLogs(),
                        true,
                        true
                ));
            }
        };
    }

    public void handleCommandInput(TextMessageEvent messageEvent, Client client){
        for( int i = 0; i < commands.size(); i++){
            if (commands.get(i).command.check(messageEvent.getMessage().toLowerCase())){
                if (client.followClientID != -1 || commands.get(i).ignoreFollowFlag) {
                    Logger.log.fine("handleCommand :: User: " + messageEvent.getInvokerName() + "Input: " + messageEvent.getMessage());
                    if (commands.get(i).requiresAllDiceAdminGroup){
                        if (Helper.isUserAllDiceAdmin(messageEvent, client)){
                            Helper.sendMessage(messageEvent, client, "Command requires elevated permissions - Executing ...", false);
                            commands.get(i).command.execute(messageEvent, client);
                        } else {
                            Helper.sendMessage(messageEvent, client, "Command requires elevated permissions - Execution denied ...", false);
                        }
                    } else {
                        commands.get(i).command.execute(messageEvent, client);
                    }
                }
            }
        }
    }
}
