package nl.tacticaldev.essentials.perm;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.config.EssentialsConfig;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.perm.impl.IPermissionsHandler;
import nl.tacticaldev.essentials.player.EssentialsPlayer;

public class PermissionsHandler implements IPermissionsHandler {

    private final EssentialsConfig config;
    private final IEssentials ess;

    public PermissionsHandler(IEssentials ess) {
        this.ess = ess;
        config = new EssentialsConfig("permissions");

        reloadConfig();
    }

    @Override
    public EssentialsConfig getConfig() {
        return config;
    }

    @Override
    public boolean hasPermission(EssentialsPlayer base, String permission) {
        return base.isAuthorized(permission);
    }

    @Override
    public boolean hasPermission(String base, String permission) {
        EssentialsPlayer essentialsPlayer = new EssentialsPlayer(base);

        return essentialsPlayer.isAuthorized(permission);
    }

    @Override
    public void reloadConfig() {
        config.reload();
    }

    @Override
    public String getName() {
        return config.getFile().getName();
    }
}
