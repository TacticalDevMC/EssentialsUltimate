package nl.tacticaldev.essentials.managers.spawn;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.config.EssentialsConfig;
import essentialsapi.interfaces.IDatabase;
import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.database.sql.SQLManager;
import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

import java.util.*;

public class Spawns implements IConf {

    private transient IEssentials ess;
    private transient IDatabase database;
    private SQLManager sqlManager;
    private transient EssentialsConfig config;
    private final Map<StringIgnoreCase, EssentialsConfig> spawnPoints = new HashMap<>();

    private ConfigurationSection spawns;

    public Spawns(IEssentials ess) {
        this.ess = ess;
        config = new EssentialsConfig("spawns");

        reloadConfig();
    }

    public EssentialsConfig getConfig() {
        return config;
    }

    @Override
    public void reloadConfig() {
        spawns = _getSpawns();
        spawnPoints.clear();
        try {
            String name = config.getConfiguration().getString("spawns");
            config.reload();
            if (name != null) {
                spawnPoints.put(new StringIgnoreCase(name), config);
            }
        } catch (Exception ex) {
            Logger.WARNING.log("Spawns load error!");
        }
    }

    public boolean isEmpty() {
        return spawnPoints.isEmpty();
    }

    public int getCount() {
        return getList().size();
    }

    private ConfigurationSection _getSpawns() {
        if (config.getConfiguration().isConfigurationSection("spawns")) {
            final ConfigurationSection section = config.getConfiguration().getConfigurationSection("spawns");
            final ConfigurationSection newSection = new MemoryConfiguration();
            for (String spawns : section.getKeys(false)) {
                if (section.isConfigurationSection(spawns)) {
                    newSection.set(spawns.toLowerCase(Locale.ENGLISH), section.getConfigurationSection(spawns));
                }
            }
            return newSection;
        }
        return null;
    }

    public String matchSpawn(String name) {
        if (config.getConfiguration().isConfigurationSection("spawns")) {
            final ConfigurationSection section = config.getConfiguration().getConfigurationSection("spawns");
            if (section != null) {
                for (String spawnName : section.getKeys(false)) {
                    if (spawnName.equalsIgnoreCase(name)) {
                        return spawnName;
                    }
                }
            }
        }
        return null;
    }

    public ConfigurationSection getSpawns() {
        return spawns;
    }

    public Map<String, Object> getSpawn(String name) {
        if (getSpawns() != null) {
            final ConfigurationSection spawns = getSpawns();
            // For some reason, YAML doesn't sees keys as always lowercase even if they aren't defined like that.
            // Workaround is to toLowercase when getting from the config, but showing normally elsewhere.
            // ODDLY ENOUGH when you get the configuration section for ALL kits, it will return the proper
            // case of each kit. But when you check for each kit's configuration section, it won't return the kit
            // you just found if you don't toLowercase it.
            if (spawns.isConfigurationSection(name.toLowerCase())) {
                return spawns.getConfigurationSection(name.toLowerCase()).getValues(true);
            }
        }
        return null;
    }

    public Location getSpawnLocation(String name) {
        String world = null;
        int x = 0, y = 0, z = 0;
        float yaw = 0, pitch = 0;
        if (getSpawns() != null) {
            if (getSpawn(name) != null) {
                world = config.getConfiguration().getString("spawns." + name + ".location.world");
                x = config.getConfiguration().getInt("spawns." + name + ".location.x");
                y = config.getConfiguration().getInt("spawns." + name + ".location.y");
                z = config.getConfiguration().getInt("spawns." + name + ".location.z");
                yaw = config.getConfiguration().getInt("spawns." + name + ".location.yaw");
                pitch = config.getConfiguration().getInt("spawns." + name + ".location.pitch");
            }
        }
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public void addSpawn(String name, World world, int x, int y, int z, float yaw, float pitch) {
        // Will overwrite but w/e
        config.getConfiguration().set("spawns." + name + ".location.world", world.getName());
        config.getConfiguration().set("spawns." + name + ".location.x", x);
        config.getConfiguration().set("spawns." + name + ".location.y", y);
        config.getConfiguration().set("spawns." + name + ".location.z", z);
        config.getConfiguration().set("spawns." + name + ".location.yaw", yaw);
        config.getConfiguration().set("spawns." + name + ".location.pitch", pitch);
        spawns = _getSpawns();

        spawnPoints.put(new StringIgnoreCase(name), config);
        config.save();
    }

    public void removeSpawn(String name) {
        config.getConfiguration().set("spawns." + name, null);
        spawns = _getSpawns();

        spawnPoints.remove(new StringIgnoreCase(name));
        config.save();
    }

    public Collection<String> getList() {
        final List<String> keys = new ArrayList<>();
        for (StringIgnoreCase stringIgnoreCase : spawnPoints.keySet()) {
            keys.add(stringIgnoreCase.getString());
        }
        keys.sort(String.CASE_INSENSITIVE_ORDER);
        return keys;
    }

    private static class StringIgnoreCase {
        private final String string;

        public StringIgnoreCase(String string) {
            this.string = string;
        }

        @Override
        public int hashCode() {
            return getString().toLowerCase(Locale.ENGLISH).hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof StringIgnoreCase) {
                return getString().equalsIgnoreCase(((StringIgnoreCase) o).getString());
            }
            return false;
        }

        public String getString() {
            return string;
        }

    }

    @Override
    public String getName() {
        return config.getFile().getName();
    }
}
