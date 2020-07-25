package essentialsapi.config;

import nl.tacticaldev.essentials.Essentials;
import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.managers.warp.exceptions.InvalidWorldException;
import org.bukkit.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class EssentialsConfig {

    private File folder;
    private String fileName;
    private boolean def;
    public FileConfiguration configuration;
    private File configFile;

    public EssentialsConfig(File dir, String name, boolean def) {
        this.folder = dir;
        if (name == null) {
            this.fileName = "No name found";
            name = "No name";
            return;
        }

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

    public EssentialsConfig(File file) {
        this(file, null, true);
    }

    public EssentialsConfig(File dir, String name) {
        this(dir, name, true);
    }

    public FileConfiguration getConfiguration() {
        if (this.configuration == null) reload();
        return this.configuration;
    }

    public Location getLocation(final String path, final Server server) throws InvalidWorldException {
        final String worldString = (path == null ? "" : path + ".") + "world";
        final String worldName = this.getConfiguration().getString(worldString);
        if (worldName == null || worldName.isEmpty()) {
            return null;
        }
        final World world = server.getWorld(worldName);
        if (world == null) {
            throw new InvalidWorldException(worldName);
        }
        return new Location(world, this.getConfiguration().getDouble((path == null ? "" : path + ".") + "x", 0), this.getConfiguration().getDouble((path == null ? "" : path + ".") + "y", 0), this.getConfiguration().getDouble((path == null ? "" : path + ".") + "z", 0), (float) this.getConfiguration().getDouble((path == null ? "" : path + ".") + "yaw", 0), (float) this.getConfiguration().getDouble((path == null ? "" : path + ".") + "pitch", 0));
    }

    public Set<String> getKeys(boolean deep) {
        return getConfiguration().getKeys(deep);
    }

    public Map<String, Object> getValues(boolean deep) {
        return getConfiguration().getValues(deep);
    }

    public boolean contains(String path) {
        return getConfiguration().contains(path);
    }

    public boolean contains(String path, boolean ignoreDefault) {
        return getConfiguration().contains(path, ignoreDefault);
    }

    public boolean isSet(String path) {
        return getConfiguration().isSet(path);
    }

    public String getCurrentPath() {
        return getConfiguration().getCurrentPath();
    }

    public void addDefault(String path, Object value) {
        getConfiguration().addDefault(path, value);
    }

    public ConfigurationSection getDefaultSection() {
        return getConfiguration().getDefaultSection();
    }

    public void set(String path, Object value) {
        getConfiguration().set(path, value);
    }

    public Object get(String path) {
        return getConfiguration().get(path);
    }

    public Object get(String path, Object def) {
        return get(path, def);
    }

    public ConfigurationSection createSection(String path) {
        return getConfiguration().createSection(path);
    }

    public ConfigurationSection createSection(String path, Map<?, ?> map) {
        return getConfiguration().createSection(path, map);
    }

    public String getString(String path) {
        return getConfiguration().getString(path);
    }

    public String getString(String path, String def) {
        return getConfiguration().getString(path, def);
    }

    public boolean isString(String path) {
        return getConfiguration().isString(path);
    }

    public int getInt(String path) {
        return getConfiguration().getInt(path);
    }

    public int getInt(String path, int def) {
        return getConfiguration().getInt(path, def);
    }

    public boolean isInt(String path) {
        return getConfiguration().isInt(path);
    }

    public boolean getBoolean(String path) {
        return getConfiguration().getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return getConfiguration().getBoolean(path, def);
    }

    public boolean isBoolean(String path) {
        return getConfiguration().isBoolean(path);
    }

    public double getDouble(String path) {
        return getConfiguration().getDouble(path);
    }

    public double getDouble(String path, double def) {
        return getConfiguration().getDouble(path, def);
    }

    public boolean isDouble(String path) {
        return getConfiguration().isDouble(path);
    }

    public long getLong(String path) {
        return getConfiguration().getLong(path);
    }

    public long getLong(String path, long def) {
        return getConfiguration().getLong(path, def);
    }

    public boolean isLong(String path) {
        return getConfiguration().isLong(path);
    }

    public List<?> getList(String path) {
        return getConfiguration().getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        return getConfiguration().getList(path, def);
    }

    public boolean isList(String path) {
        return getConfiguration().isList(path);
    }

    public List<String> getStringList(String path) {
        return getConfiguration().getStringList(path);
    }

    public List<Integer> getIntegerList(String path) {
        return getConfiguration().getIntegerList(path);
    }

    public List<Boolean> getBooleanList(String path) {
        return getConfiguration().getBooleanList(path);
    }

    public List<Double> getDoubleList(String path) {
        return getConfiguration().getDoubleList(path);
    }

    public List<Float> getFloatList(String path) {
        return getConfiguration().getFloatList(path);
    }

    public List<Long> getLongList(String path) {
        return getConfiguration().getLongList(path);
    }

    public List<Byte> getByteList(String path) {
        return getConfiguration().getByteList(path);
    }

    public List<Character> getCharacterList(String path) {
        return getConfiguration().getCharacterList(path);
    }

    public List<Short> getShortList(String path) {
        return getConfiguration().getShortList(path);
    }

    public List<Map<?, ?>> getMapList(String path) {
        return getConfiguration().getMapList(path);
    }

    public Vector getVector(String path) {
        return getConfiguration().getVector(path);
    }

    public Vector getVector(String path, Vector def) {
        return getConfiguration().getVector(path, def);
    }

    public boolean isVector(String path) {
        return getConfiguration().isVector(path);
    }

    public OfflinePlayer getOfflinePlayer(String path) {
        return getConfiguration().getOfflinePlayer(path);
    }

    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
        return getConfiguration().getOfflinePlayer(path, def);
    }

    public boolean isOfflinePlayer(String path) {
        return getConfiguration().isOfflinePlayer(path);
    }

    public ItemStack getItemStack(String path) {
        return getConfiguration().getItemStack(path);
    }

    public ItemStack getItemStack(String path, ItemStack def) {
        return getConfiguration().getItemStack(path, def);
    }

    public boolean isItemStack(String path) {
        return getConfiguration().isItemStack(path);
    }

    public Color getColor(String path) {
        return getConfiguration().getColor(path);
    }

    public Color getColor(String path, Color def) {
        return getConfiguration().getColor(path, def);
    }

    public boolean isColor(String path) {
        return getConfiguration().isColor(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return getConfiguration().getConfigurationSection(path);
    }

    public boolean isConfigurationSection(String path) {
        return getConfiguration().isConfigurationSection(path);
    }

    public void setProperty(final String path, final Location loc) {
        this.getConfiguration().set((path == null ? "" : path + ".") + "world", loc.getWorld().getName());
        this.getConfiguration().set((path == null ? "" : path + ".") + "x", loc.getX());
        this.getConfiguration().set((path == null ? "" : path + ".") + "y", loc.getY());
        this.getConfiguration().set((path == null ? "" : path + ".") + "z", loc.getZ());
        this.getConfiguration().set((path == null ? "" : path + ".") + "yaw", loc.getYaw());
        this.getConfiguration().set((path == null ? "" : path + ".") + "pitch", loc.getPitch());
    }

    public boolean hasProperty(final String path) {
        return this.getConfiguration().isSet(path);
    }

    public void setProperty(final String path, final ItemStack stack) {
        final Map<String, Object> map = new HashMap<>();
        map.put("type", stack.getType().toString());
        map.put("amount", stack.getAmount());
        map.put("damage", stack.getDurability());
        Map<Enchantment, Integer> enchantments = stack.getEnchantments();
        if (!enchantments.isEmpty()) {
            Map<String, Integer> enchant = new HashMap<>();
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                enchant.put(entry.getKey().getName().toLowerCase(Locale.ENGLISH), entry.getValue());
            }
            map.put("enchant", enchant);
        }
        // getData().getData() is broken
        //map.put("data", stack.getDurability());
        this.getConfiguration().set(path, map);
    }

    public void setProperty(String path, List object) {
        this.getConfiguration().set(path, new ArrayList(object));
    }

    public void setProperty(String path, Map object) {
        this.getConfiguration().set(path, new LinkedHashMap(object));
    }

    public Object getProperty(String path) {
        return this.getConfiguration().get(path);
    }

    public void setProperty(final String path, final BigDecimal bigDecimal) {
        this.getConfiguration().set(path, bigDecimal.toString());
    }

    public void setProperty(String path, Object object) {
        this.getConfiguration().set(path, object);
    }

    public void removeProperty(String path) {
        this.getConfiguration().set(path, null);
    }

    public synchronized BigDecimal getBigDecimal(final String path, final BigDecimal def) {
        final String input = this.getConfiguration().getString(path);
        return toBigDecimal(input, def);
    }

    public static BigDecimal toBigDecimal(final String input, final BigDecimal def) {
        if (input == null || input.isEmpty()) {
            return def;
        } else {
            try {
                return new BigDecimal(input, MathContext.DECIMAL128);
            } catch (NumberFormatException | ArithmeticException e) {
                return def;
            }
        }
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