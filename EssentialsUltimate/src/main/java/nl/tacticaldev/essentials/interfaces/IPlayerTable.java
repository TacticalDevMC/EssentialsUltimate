package nl.tacticaldev.essentials.interfaces;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.util.UUID;

public interface IPlayerTable {

    Connection getConnection();

    void insertPlayer(Player player);

    void deletePlayer(UUID uuid);

    boolean existsPlayer(UUID uuid);

}
