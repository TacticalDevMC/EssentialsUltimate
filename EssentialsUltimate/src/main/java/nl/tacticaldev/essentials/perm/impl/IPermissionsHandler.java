package nl.tacticaldev.essentials.perm.impl;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.config.EssentialsConfig;
import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.player.EssentialsPlayer;

public interface IPermissionsHandler extends IConf {

    EssentialsConfig getConfig();

    boolean hasPermission(EssentialsPlayer base, String permission);

    boolean hasPermission(String base, String permission);

}
