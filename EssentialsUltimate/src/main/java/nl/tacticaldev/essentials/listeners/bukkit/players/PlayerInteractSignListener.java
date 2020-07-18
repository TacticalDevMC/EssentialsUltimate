package nl.tacticaldev.essentials.listeners.bukkit.players;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Utils;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.managers.spawn.Spawns;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractSignListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();

                if (sign.getLine(0).equals(Utils.replaceColor("&7[&6EssentialsSigns&7]")) && sign.getLine(1).equals(Utils.replaceColor("&9Spawn")) && sign.getLine(2) != null) {
                    Spawns spawns = Essentials.getInstance().getSpawns();

                    String spawnName = ChatColor.stripColor(sign.getLine(2));

                    System.out.println(sign.getLine(0));
                    System.out.println(sign.getLine(1));
                    System.out.println(sign.getLine(2));

                    System.out.println(spawnName);

                    if (spawns.getSpawn(spawnName) == null) {
                        sign.setLine(2, Utils.replaceColor("&cSpawn can not be"));
                        sign.setLine(3, Utils.replaceColor("&cfound anymore!"));
                        sign.update();
                        return;
                    }

                    EssentialsPlayer base = new EssentialsPlayer(player);

                    Location location = spawns.getSpawnLocation(spawnName);

                    base.teleport(location);
                }
            }
        }
    }
}
