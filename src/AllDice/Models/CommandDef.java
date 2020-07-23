package AllDice.Models;

import AllDice.Helper.Helper;

public class CommandDef {
    public int index;
    public String name;
    public String syntax;
    public String description;
    public String example;
    public Command command;
    public boolean ignoreFollowFlag;
    public boolean requiresAllDiceAdminGroup;

    public CommandDef(String _name, String _syntax, String _description, String _example, Command _command, boolean _ignoreFollowFlag, boolean _requiresAllDiceAdminGroup){
        index = Helper.commandIndexCounter++;
        name = _name;
        syntax = _syntax;
        description = _description;
        example = _example;
        command = _command;
        ignoreFollowFlag = _ignoreFollowFlag;
        requiresAllDiceAdminGroup = _requiresAllDiceAdminGroup;
    }
}
