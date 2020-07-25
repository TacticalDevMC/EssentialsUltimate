package nl.tacticaldev.essentials.managers.warp;

import essentialsapi.config.EssentialsConfig;
import essentialsapi.utils.essentialsutils.StringUtil;
import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.managers.warp.exceptions.InvalidNameException;
import nl.tacticaldev.essentials.managers.warp.exceptions.InvalidWorldException;
import nl.tacticaldev.essentials.managers.warp.exceptions.WarpNotFoundException;
import nl.tacticaldev.essentials.managers.warp.interfaces.IWarps;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Location;
import org.bukkit.Server;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Warps implements IConf, IWarps {
    private final Map<StringIgnoreCase, EssentialsConfig> warpPoints = new HashMap<>();
    private final File warpsFolder;
    private final Server server;

    public Warps(Server server, File dataFolder) {
        this.server = server;
        warpsFolder = new File(dataFolder, "warps");
        if (!warpsFolder.exists()) {
            warpsFolder.mkdirs();
        }
        reloadConfig();
    }

    @Override
    public boolean isEmpty() {
        return warpPoints.isEmpty();
    }

    @Override
    public Collection<String> getList() {
        final List<String> keys = new ArrayList<>();
        for (StringIgnoreCase stringIgnoreCase : warpPoints.keySet()) {
            keys.add(stringIgnoreCase.getString());
        }
        keys.sort(String.CASE_INSENSITIVE_ORDER);
        return keys;
    }

    @Override
    public Location getWarp(String warp) throws WarpNotFoundException, InvalidWorldException {
        EssentialsConfig config = warpPoints.get(new StringIgnoreCase(warp));
        if (config == null) {
            throw new WarpNotFoundException();
        }
        return config.getLocation(null, server);
    }

    @Override
    public void setWarp(String name, Location loc) throws Exception {
        setWarp(null, name, loc);
    }

    @Override
    public void setWarp(EssentialsPlayer base, String name, Location loc) throws Exception {
        String fileName = StringUtil.sanitizeFileName(name);
        EssentialsConfig config = warpPoints.get(new StringIgnoreCase(name));
        if (config == null) {
            File confFile = new File(warpsFolder, fileName + ".yml");
            if (confFile.exists()) {
                throw new Exception("Similar warp exists");
            }
            config = new EssentialsConfig(confFile, name);
            warpPoints.put(new StringIgnoreCase(name), config);
        }
        config.setProperty(null, loc);
        config.setProperty("name", name);

        if (base != null) config.setProperty("lastowner", base.getBase().getUniqueId().toString());

        config.save();
    }

    @Override
    public UUID getLastOwner(String warp) throws WarpNotFoundException {
        EssentialsConfig config = warpPoints.get(new StringIgnoreCase(warp));
        if (config == null) {
            throw new WarpNotFoundException();
        }

        UUID uuid = null;
        try {
            uuid = UUID.fromString(config.getConfiguration().getString("lastowner"));
        } catch (Exception ignored) {
        }
        return uuid;
    }

    @Override
    public void removeWarp(String name) throws Exception {
        EssentialsConfig config = warpPoints.get(new StringIgnoreCase(name));
        if (config == null) {
            throw new Exception("Warp not exists!");
        }
        if (!config.getFile().delete()) {
            throw new Exception("Warp delete error.");
        }
        warpPoints.remove(new StringIgnoreCase(name));
    }

    @Override
    public void reloadConfig() {
        warpPoints.clear();
        File[] listOffFiles = warpsFolder.listFiles();
        if (listOffFiles.length >= 1) {
            for (File listOfFile : listOffFiles) {
                String filename = listOfFile.getName();
                if (listOfFile.isFile() && filename.endsWith(".yml")) {
                    try {
                        EssentialsConfig config = new EssentialsConfig(listOfFile, filename);
                        config.reload();
                        String name = config.getConfiguration().getString("name");
                        if (name != null) {
                            warpPoints.put(new StringIgnoreCase(name), config);
                        }
                    } catch (Exception ex) {
                        Logger.ERROR.log("Load warp error " + filename);
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return "warps";
    }

    //This is here for future 3.x api support. Not implemented here becasue storage is handled differently
    @Override
    public File getWarpFile(String name) throws InvalidNameException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getCount() {
        return getList().size();
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
}
