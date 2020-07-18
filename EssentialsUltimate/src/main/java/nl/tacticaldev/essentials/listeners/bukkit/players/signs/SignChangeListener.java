package nl.tacticaldev.essentials.listeners.bukkit.players.signs;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.listeners.custom.EssentialsSignsEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {

        EssentialsSignsEvent signsEvent = new EssentialsSignsEvent(event.getBlock(), event.getPlayer(), event.getLines());
        Bukkit.getPluginManager().callEvent(signsEvent);
    }
}
