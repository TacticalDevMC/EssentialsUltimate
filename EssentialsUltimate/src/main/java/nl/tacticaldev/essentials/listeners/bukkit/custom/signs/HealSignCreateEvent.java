package nl.tacticaldev.essentials.listeners.bukkit.custom.signs;

import essentialsapi.utils.Utils;
import nl.tacticaldev.essentials.listeners.custom.EssentialsSignsEvent;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HealSignCreateEvent implements Listener {

    @EventHandler
    public void onEssentialsSigns(EssentialsSignsEvent event) {
        EssentialsPlayer base = event.getPlayer();
        Block block = event.getBlock();

        if (event.getLine(0).equals("[EssentialsSign]") && event.getLine(1).equals("[heal]") && event.getLine(2).equals("clicker")) {
            event.setLine(0, Utils.replaceColor("&7[&6EssentialsSigns&7]"));
            event.setLine(1, Utils.replaceColor("&9Heal"));
            event.setLine(2, Utils.replaceColor("&2&lClicker"));
        } else if (event.getLine(0).equals("[EssentialsSign]") && event.getLine(1).equals("[heal]") && event.getLine(2) != null) {
            event.setLine(0, Utils.replaceColor("&7[&6EssentialsSigns&7]"));
            event.setLine(1, Utils.replaceColor("&9Heal"));
            event.setLine(2, Utils.replaceColor("&2&l" + event.getLine(2)));
        }
    }
}
