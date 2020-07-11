package nl.tacticaldev.essentials.commands;

import essentialsapi.utils.exception.CoreException;

import java.util.ArrayList;

public abstract class SubCommand {


    public SubCommand(ArrayList<String> commands, String permission, String info) {
        this.commands = commands;
        this.mainCommand = commands.get(0);
        this.permission = permission;
        this.info = info;
    }

    public SubCommand(String[] command, String permission, String info) {
        for (String s : command) {
            this.commands.add(s);
        }
        this.mainCommand = command[0];
        this.permission = permission;
        this.info = info;
    }

    public SubCommand(String command, String permission, String info) {
        this(new String[]{command}, permission, info);
    }

    public SubCommand(String command, String permission, String info, boolean allowConsole) {
        this(command, permission, info);
        this.allowConsole = allowConsole;
    }


    private boolean allowConsole = true;
    private String info;
    private String permission;

    private String mainCommand;

    public String getMainCommand() {
		return mainCommand;
	}
    
    public String getInfo() {
		return info;
	}
    
    public String getPermission() {
		return permission;
	}
    
    public ArrayList<String> commands = new ArrayList<String>();

    public abstract void execute(String[] args) throws CoreException;

    public boolean allowConsole() {
        return allowConsole;
    }

    public void setAllowConsole(boolean allow) {
        this.allowConsole = allow;
    }
}