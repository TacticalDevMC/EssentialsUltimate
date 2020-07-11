package essentialsapi.interfaces;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public interface IDatabase {

    void tryToStart();

    void start();

    void stop();

    void restart();

    HikariDataSource getHikari();

    Connection getConnection();

    boolean isLoaded();

}
