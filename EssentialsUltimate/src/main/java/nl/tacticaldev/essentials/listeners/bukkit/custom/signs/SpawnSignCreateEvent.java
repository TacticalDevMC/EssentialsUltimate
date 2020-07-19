package nl.tacticaldev.essentials.listeners.bukkit.custom.signs;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Utils;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.listeners.custom.EssentialsSignsEvent;
import nl.tacticaldev.essentials.managers.spawn.Spawns;
import nl.tacticaldev.essentials.managers.spawn.exception.SpawnNotFoundException;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SpawnSignCreateEvent implements Listener {

    @EventHandler
    public void onEssentialsSigns(EssentialsSignsEvent event) {
        EssentialsPlayer player = event.getPlayer();

        if (event.getLine(0).equals("[EssentialsSign]") && event.getLine(1).equals("[spawn]") && event.getLine(2) != null) {
            event.setLine(0, Utils.replaceColor("&7[&6EssentialsSigns&7]"));
            event.setLine(1, Utils.replaceColor("&9Spawn"));
            Spawns spawns = Essentials.getInstance().getSpawns();
            if (spawns.getSpawn(event.getLine(2)) != null) {
                event.setLine(2, Utils.replaceColor("&2&l" + spawns.matchSpawn(event.getLine(2))));
            } else {
                try {
                    throw new SpawnNotFoundException("The spawn on line 2 can not be found");
                } catch (SpawnNotFoundException e) {
                    player.sendMessage(Utils.replaceColor("&cThe spawn on line 2 can not be found!"));
                }
            }
        }
    }
}
