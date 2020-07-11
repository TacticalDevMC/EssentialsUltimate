package nl.tacticaldev.essentials.geoip;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import essentialsapi.config.EssentialsConfig;
import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.codehaus.plexus.archiver.tar.TarEntry;
import org.codehaus.plexus.archiver.tar.TarInputStream;

import java.io.*;
import java.net.*;
import java.util.zip.GZIPInputStream;

import static essentialsapi.utils.Utils.replaceColor;

public class EssentialsGeoIPPlayerListener implements Listener, IConf {

    private DatabaseReader mmreader = null; // initialize maxmind geoip2 reader
    private File databaseFile;
    private File dataFolder;
    private final EssentialsConfig config;
    private final transient IEssentials ess;

    EssentialsGeoIPPlayerListener(File datafolder, IEssentials ess) {
        this.ess = ess;
        this.dataFolder = datafolder;
        this.config = new EssentialsConfig(datafolder, "config");
        reloadConfig();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        ess.runTaskAsynchronously(() -> delayedJoin(event.getPlayer()));
    }

    private void delayedJoin(Player player) {
        EssentialsPlayer base = new EssentialsPlayer(player);
        if (base.isAuthorized("essentials.geoip.hide") || player.getAddress() == null) {
            return;
        }
        InetAddress address = player.getAddress().getAddress();
        StringBuilder sb = new StringBuilder();

        try {
            if (config.getConfiguration().getBoolean("database.show-cities", false)) {
                CityResponse response = mmreader.city(address);

                if (response == null) {
                    return;
                }
                String city;
                String region;
                String country;
                city = response.getCity().getName();
                region = response.getMostSpecificSubdivision().getName();
                country = response.getCountry().getName();
                if (city != null) {
                    sb.append(city).append(",");
                }
                if (region != null) {
                    sb.append(region).append(", ");
                }
                sb.append(country);
            } else {
                CountryResponse response = mmreader.country(address);
                sb.append(response.getCountry().getName());
            }
        } catch (AddressNotFoundException ex) {
            if (checkIfLocal(address)) {
                for (Player online : player.getServer().getOnlinePlayers()) {
                    EssentialsPlayer baseOnline = new EssentialsPlayer(online);
                    if (baseOnline.isAuthorized("essentials.geoip.show")) {
                        baseOnline.sendMessage(replaceColor("&cPlayer &4" + baseOnline.getBase().getName() + " &ccomes from an unknown country!"));
                    }
                }
                return;
            }
            Logger.INFO.log("Can't read GeoIPDB" + " " + ex.getLocalizedMessage());
        } catch (GeoIp2Exception | IOException ex) {
            Logger.INFO.log("Can't read GeoIPDB" + " " + ex.getLocalizedMessage());
        }

        if (config.getConfiguration().getBoolean("show-on-login", true)) {
            for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
                EssentialsPlayer user = new EssentialsPlayer(onlinePlayer);
                if (user.isAuthorized("essentials.geoip.show")) {
                    user.sendMessage(replaceColor("&6Player &c" + user.getBase().getName() + " &6comes from &c" + sb.toString() + " &6."));
                }
            }
        }
    }

    public void reloadConfig() {
        config.reload();

        if (!config.getConfiguration().isSet("enable-locale")) {
            config.getConfiguration().set("database.download-url", "https://download.maxmind.com/app/geoip_download?edition_id=GeoLite2-Country&license_key={LICENSEKEY}&suffix=tar.gz");
            config.getConfiguration().set("database.download-url-city", "https://download.maxmind.com/app/geoip_download?edition_id=GeoLite2-City&license_key={LICENSEKEY}&suffix=tar.gz");
            config.getConfiguration().set("database.license-key", "");
            config.getConfiguration().set("database.update.enable", true);
            config.getConfiguration().set("database.update.by-every-x-days", 30);
            config.getConfiguration().set("enable-locale", true);
            config.save();
            // delete old GeoIP.dat fiiles
            File oldDatFile = new File(dataFolder, "GeoIP.dat");
            File oldDatFileCity = new File(dataFolder, "GeoIP-City.dat");
            oldDatFile.delete();
            oldDatFileCity.delete();
        }

        if (config.getConfiguration().getBoolean("database.show-cities", false)) {
            databaseFile = new File(dataFolder, "GeoIP2-City.mmdb");
        } else {
            databaseFile = new File(dataFolder, "GeoIP2-Country.mmdb");
        }
        if (!databaseFile.exists()) {
            if (config.getConfiguration().getBoolean("database.download-if-missing", true)) {
                downloadDatabase();
            } else {
                Logger.INFO.log("Can't find GeoIPDB!");
            }
        }
    }

    private void downloadDatabase() {
        try {
            String url;
            if (config.getConfiguration().getBoolean("database.show-cities", false)) {
                url = config.getConfiguration().getString("database.download-url-city");
            } else {
                url = config.getConfiguration().getString("database.download-url");
            }
            if (url == null || url.isEmpty()) {
                Logger.ERROR.log("GeoIP url is empty");
                return;
            }
            String licenseKey = config.getConfiguration().getString("database.license-key", "");
            if (licenseKey == null || licenseKey.isEmpty()) {
                Logger.ERROR.log("GeoIP license is missing");
                return;
            }
            url = url.replace("{LICENSEKEY}", licenseKey);
            Logger.ERROR.log("Downloading GeoIP");
            URL downloadUrl = new URL(url);
            URLConnection conn = downloadUrl.openConnection();
            conn.setConnectTimeout(10000);
            conn.connect();
            InputStream input = conn.getInputStream();
            OutputStream output = new FileOutputStream(databaseFile);
            byte[] buffer = new byte[2048];
            if (url.contains("gz")) {
                input = new GZIPInputStream(input);
                if (url.contains("tar.gz")) {
                    // The new GeoIP2 uses tar.gz to pack the db file along with some other txt. So it makes things a bit complicated here.
                    String filename;
                    TarInputStream tarInputStream = new TarInputStream(input);
                    TarEntry entry;
                    while ((entry = tarInputStream.getNextEntry()) != null) {
                        if (!entry.isDirectory()) {
                            filename = entry.getName();
                            if (filename.substring(filename.length() - 5).equalsIgnoreCase(".mmdb")) {
                                input = tarInputStream;
                                break;
                            }
                        }
                    }
                }
            }
            int length = input.read(buffer);
            while (length >= 0) {
                output.write(buffer, 0, length);
                length = input.read(buffer);
            }
            output.close();
            input.close();
        } catch (MalformedURLException ex) {
            Logger.ERROR.log("GeoIP url is invalid");
        } catch (IOException ex) {
            Logger.ERROR.log("GeoIP Connection failed");
        }
    }

    public String getName() {
        return config.getFile().getName();
    }

    private boolean checkIfLocal(InetAddress address) {
        if (address.isAnyLocalAddress() || address.isLoopbackAddress()) {
            return true;
        }

        // Double checks if address is defined on any interface
        try {
            return NetworkInterface.getByInetAddress(address) != null;
        } catch (SocketException e) {
            return false;
        }
    }
}
