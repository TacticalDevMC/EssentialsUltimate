package nl.tacticaldev.essentials.commands.lang;

import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.commands.SubCommand;
import nl.tacticaldev.essentials.menus.LangMenu;

import java.util.List;


public class LangCommand extends CoreCommand {

    public LangCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    List<String> netherlandsLore = null;

    @Override
    public void init() {
        this.registerSub(new SubCommand( "menu", "essentials.lang.menu", "Open the menu of all the languages") {
            @Override
            public void execute(String[] args) throws CoreException {
                LangMenu langMenu = new LangMenu(getPlayer());

                langMenu.open();
            }
        });
    }

    @Override
    public void execute() throws CoreException {

    }
}
