package essentialsapi.config;

import nl.tacticaldev.essentials.Essentials;
import essentialsapi.utils.logger.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EssentialsConfig {

    private File folder;
    private String fileName;
    private boolean def;
    public FileConfiguration configuration;
    private File configFile;

    public EssentialsConfig(File dir, String name, boolean def) {
        this.folder = dir;
        this.fileName = name + (name.endsWith(".yml") ? "" : ".yml");
        this.def = def;

        reload();
    }

    public EssentialsConfig(String name, boolean isDef) {
        this(Essentials.getInstance().getDataFolder(), name, isDef);
    }

    public EssentialsConfig(String name) {
        this(Essentials.getInstance().getDataFolder(), name, true);
    }

    public EssentialsConfig(File dir, String name) {
        this(dir, name, true);
    }

        public FileConfiguration getConfiguration() {
        if (this.configuration == null) reload();
        return this.configuration;
    }

    public File getFile() {
        return this.configFile;
    }

    public boolean reload() {
        if (!this.folder.exists()) {
            try {
                if (this.folder.mkdir()) {
                    Logger.CONFIG.sendConsoleLog("&a - &9Created a new configuration file!");
                } else {
                    Logger.CONFIG.sendConsoleLog("&4- &cUnable to create folder " + this.folder.getName());
                }
            } catch (Exception e) {
                Logger.WARNING.log("Failed to reload " + this.fileName + " config.");
                return false;
            }
        }

        this.configFile = new File(this.folder, this.fileName);
        this.configuration = YamlConfiguration.loadConfiguration(this.configFile);

        if (this.def) {
            InputStream defaultConfigStream = Essentials.getInstance().getResource(this.fileName);

            if (defaultConfigStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));
                this.configuration.setDefaults(defaultConfig);
                saveDefaultConfig();
            }
        } else if (!this.configFile.exists()) {
            try {
                this.configFile.createNewFile();
            } catch (Exception e) {
                Logger.WARNING.log("Failed to reload " + this.fileName + " config.");
            }
        }
        return true;
    }

//    public void reload() {
//        try {
//            this.configuration = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(this.folder), StandardCharsets.UTF_8));
//        } catch (FileNotFoundException e) {
//            Logger.WARNING.log("Failed to reload " + this.fileName + " config.");
//        }
//
//        if (this.def) {
//            InputStream defaultConfigStream = EssentialsUltimate.getInstance().getResource(this.fileName);
//
//            if (defaultConfigStream != null) {
//                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));
//                this.configuration.setDefaults(defaultConfig);
//                saveDefaultConfig();
//            }
//        }
//    }

    public boolean save() {
        if (this.configuration == null || this.configFile == null) {
            return false;
        }

        try {
            getConfiguration().save(this.configFile);
            return true;
        } catch (IOException e) {
            Logger.WARNING.log("Failed to save config to " + this.configFile.getName());
            Logger.WARNING.log(e);
            return false;
        }
    }

    private void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(Essentials.getInstance().getDataFolder(), this.fileName);

        if (!this.configFile.exists()) Essentials.getInstance().saveResource(this.fileName, false);
    }
}