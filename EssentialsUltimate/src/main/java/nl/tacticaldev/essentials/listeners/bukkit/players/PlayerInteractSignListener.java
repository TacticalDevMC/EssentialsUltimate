package nl.tacticaldev.essentials.listeners.bukkit.players;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Cooldown;
import essentialsapi.utils.Utils;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.managers.spawn.Spawns;
import nl.tacticaldev.essentials.managers.spawn.exception.SpawnNotFoundException;
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
        IEssentials ess = Essentials.getInstance();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (block.getState() instanceof Sign) {
                if (ess.getSettings().isUsingCooldownsOnSign()) {
                    if (Cooldown.isInCooldown(player.getUniqueId(), "sign")) {
                        EssentialsMessages.SIGN_ON_COOLDOWN.send(player, Cooldown.getTimeLeft(player.getUniqueId(), "sign"));
                        return;
                    }

                    Cooldown c = new Cooldown(player.getUniqueId(), "sign", ess.getSettings().getCooldownOnSign());
                    c.start();
                }

                Sign sign = (Sign) block.getState();

                if (sign.getLine(0).equals(Utils.replaceColor("&7[&6EssentialsSigns&7]")) && sign.getLine(1).equals(Utils.replaceColor("&9Spawn")) && sign.getLine(2) != null) {
                    Spawns spawns = Essentials.getInstance().getSpawns();

                    String spawnName = ChatColor.stripColor(sign.getLine(2));

                    if (spawns.getSpawn(spawnName) == null) {
                        sign.setLine(2, Utils.replaceColor("&cSpawn can not be"));
                        sign.setLine(3, Utils.replaceColor("&cfound anymore!"));
                        sign.update();
                        return;
                    }

                    EssentialsPlayer base = new EssentialsPlayer(player);

                    Location location = null;
                    try {
                        location = spawns.getSpawnLocation(spawnName);
                    } catch (SpawnNotFoundException e) {
                        e.printStackTrace();
                    }

                    base.teleport(location);
                } else if (sign.getLine(0).equals(Utils.replaceColor("&7[&6EssentialsSigns&7]")) && sign.getLine(1).equals(Utils.replaceColor("&9Heal")) && sign.getLine(2).equals(Utils.replaceColor("&2&lClicker"))) {
                    EssentialsPlayer base = new EssentialsPlayer(player);

                    base.heal(20);
                    base.feed(20);
                    EssentialsMessages.SIGN_HEAL_HEALED.send(player);
                }
            }
        }
    }
}
