package nl.tacticaldev.essentials.managers.powertool;

import essentialsapi.config.EssentialsConfig;
import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.managers.powertool.interfaces.IPowerTool;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PowerTool implements IPowerTool, IConf {

    private IEssentials ess;
    private EssentialsConfig config;

    public PowerTool(IEssentials ess) {
        this.ess = ess;
        this.config = new EssentialsConfig("powertool");
        reloadConfig();
    }

    @Override
    public ItemStack getPowerTool() {
        if (!ess.getSettings().getConfig().isItemStack("powertool-item")) {
            return null;
        }
        return ess.getSettings().getConfig().getItemStack("powertool-item");
    }

    private Map<UUID, String[]> ptMap = new HashMap<>();

    @Override
    public void addCommand(EssentialsPlayer player, String[] command) {
        if (config.getString("players." + player.getUUID().toString()) == null) {
            config.set("players." + player.getUUID().toString() + ".name", player.getBase().getName());
            config.set("players." + player.getUUID().toString() + ".commands", "[]");
            config.save();

            ptMap.put(player.getUUID(), command);
        } else {
            config.set("players." + player.getUUID().toString() + ".name", player.getBase().getName());
            config.set("players." + player.getUUID().toString() + ".commands", command);
            config.save();

            ptMap.put(player.getUUID(), command);
        }
    }

    @Override
    public void removeCommand(EssentialsPlayer player, String[] command) {
        if (!(config.getString("players." + player.getUUID().toString()) == null)) {
            config.set("players." + player.getUUID().toString() + ".commands." + Arrays.toString(command), null);
            config.save();

            ptMap.remove(player.getUUID(), command);
        }
    }

    @Override
    public List<String> getPowerToolPlayers() {
        return config.getStringList("players");
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
