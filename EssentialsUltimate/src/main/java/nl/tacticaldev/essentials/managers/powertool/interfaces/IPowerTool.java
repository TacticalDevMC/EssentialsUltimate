package nl.tacticaldev.essentials.managers.powertool.interfaces;

import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IPowerTool extends IConf {

    ItemStack getPowerTool();

    void addCommand(EssentialsPlayer player, String[] command);

    void removeCommand(EssentialsPlayer player, String[] command);

    List<String> getPowerToolPlayers();

}
