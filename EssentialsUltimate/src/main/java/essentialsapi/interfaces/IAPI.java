package essentialsapi.interfaces;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.interfaces.IEssentials;

public interface IAPI {

    IDatabase getDatabase();

    void loadListeners(IEssentials ess);

    void loadCommands();

    void createTables();

}
