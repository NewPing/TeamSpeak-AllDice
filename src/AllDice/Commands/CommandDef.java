package AllDice.Commands;

import AllDice.Helper.Helper;

public class CommandDef {
    public int index;
    public String name;
    public String syntax;
    public String description;
    public String example;
    public Command command;

    public CommandDef(String _name, String _syntax, String _description, String _example, Command _command){
        index = Helper.commandIndexCounter++;
        name = _name;
        syntax = _syntax;
        description = _description;
        example = _example;
        command = _command;
    }
}
