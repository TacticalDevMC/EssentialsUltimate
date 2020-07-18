package essentialsapi.utils;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Utils {

    @Getter(AccessLevel.PUBLIC)
    @Setter
    private static Utils instance;

    private static Pattern IP_PATTERN;
    private static Pattern VALID_CHARS_PATTERN;

    public static Sound SOUND_LEVEL_UP = getVersion().startsWith("v1_8") ? Sound.valueOf("LEVEL_UP") : Sound.valueOf("ENTITY_PLAYER_LEVELUP");
    public static Sound SOUND_CHICKEN_EGG = getVersion().startsWith("v1_8") ? Sound.valueOf("CHICKEN_EGG_POP") : Sound.valueOf("ENTITY_CHICKEN_EGG");
    private final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private SecureRandom rnd = new SecureRandom();

    static {
        Utils.IP_PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        Utils.VALID_CHARS_PATTERN = Pattern.compile("[A-Za-z0-9_]");
    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public Utils() {
        setInstance(this);
    }

    public static String capitalCase(final String input) {
        return input == null || input.length() == 0 ? input : input.toUpperCase(Locale.ENGLISH).charAt(0) + input.toLowerCase(Locale.ENGLISH).substring(1);
    }

    public static boolean isSilent(final String[] args) {
        if (args == null) {
            return false;
        }
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equalsIgnoreCase("-s")) {
                for (int j = i; j < args.length - 1; ++j) {
                    args[j] = args[j + 1];
                }
                args[args.length - 1] = "";
                return true;
            }
        }
        return false;
    }

    public static String buildReason(final String[] args) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 1; i < args.length; ++i) {
            sb.append(args[i]);
            sb.append(" ");
        }
        String s = sb.toString().trim();
        s = s.replaceAll("\\\\n", "\n");
        if (s.isEmpty()) {
            return Essentials.getInstance().getBanManager().defaultReason;
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getName(final CommandSender s) {
        if (s instanceof Player) {
            return ((Player) s).getName();
        }
        return "Console";
    }

    public static boolean isIP(final String s) {
        return Utils.IP_PATTERN.matcher(s).matches();
    }

    public static String replaceColor(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public void playFirework(Player p, Location loc, Color color1, Color color2, FireworkEffect.Type type) {
        loc.add(0.5, 1, 0.5);
        Firework fw = p.getWorld().spawn(loc, Firework.class);
        FireworkMeta fwmeta = ((Firework) fw).getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();

        builder.withFlicker();
        builder.withFade(color2);
        builder.withColor(color1);
        builder.with(type);
        fwmeta.clearEffects();
        Field f;
        try {
            f = fwmeta.getClass().getDeclaredField("power");
            f.setAccessible(true);
            f.set(fwmeta, -1);
        } catch (Exception e) {
            return;
        }
        fwmeta.addEffect(builder.build());
        fw.setFireworkMeta(fwmeta);
    }

    public String getDateTime() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss(dd/MM/yyyy)");
        return ft.format(date);
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        return ft.format(date);
    }

    public String getDate() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
        return ft.format(date);
    }


    public String getLocation(Location loc) {
        return loc.getBlock() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
    }

    public Location getNewLocation(Location oldLocation) {
        return new Location(oldLocation.getWorld(), oldLocation.getBlockX(), oldLocation.getBlockY(), oldLocation.getBlockZ());
    }

    public Entity[] getNearbyEntities(Location l, int radius) {
        int chunkRadius = radius < 16 ? 1 : (radius - radius % 16) / 16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
                int x = (int) l.getX();
                int y = (int) l.getY();
                int z = (int) l.getZ();
                for (Entity e : new Location(l.getWorld(), x + chX * 16, y, z + chZ * 16).getChunk().getEntities()) {
                    if ((e.getLocation().distance(l) <= radius) && (e.getLocation().getBlock() != l.getBlock())) {
                        radiusEntities.add(e);
                    }
                }
            }
        }
        return (Entity[]) radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }

    public String generateRandomString(int size) {
        int l = size;
        StringBuilder sb = new StringBuilder(l);
        for (int i = 0; i < l; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public boolean equalsLocation(Location loc1, Location loc2) {
        return (loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY()
                && loc1.getBlockZ() == loc2.getBlockZ());
    }

    public List<Location> drawCircle(Location loc, Integer radius, Integer height, Boolean hollow, Boolean sphere,
                                     int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        Integer r = radius;
        Integer h = height;
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r.intValue(); x <= cx + r.intValue(); x++) {
            for (int z = cz - r.intValue(); z <= cz + r.intValue(); z++) {
                for (int y = sphere.booleanValue() ? cy - r.intValue() : cy; y < (sphere.booleanValue()
                        ? cy + r.intValue() : cy + h.intValue()); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z)
                            + (sphere.booleanValue() ? (cy - y) * (cy - y) : 0);
                    if ((dist < r.intValue() * r.intValue())
                            && ((!hollow.booleanValue()) || (dist >= (r.intValue() - 1) * (r.intValue() - 1)))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public ArrayList<String> getLore(String lore) {
        ArrayList<String> newlore = new ArrayList<String>();
        newlore.add(lore);
        return newlore;
    }

    public ArrayList<String> convert(ArrayList<Object> list) {
        ArrayList<String> temp = new ArrayList<String>();
        for (Object o : list) {
            if (o instanceof String) {
                String s = (String) o;
                temp.add(s);
            }

        }
        return temp;
    }

    public String locationToDbString(Location loc) {
        return loc.getBlockX() + "-" + loc.getBlockY() + "-" + loc.getBlockZ() + "-" + loc.getPitch() + "-"
                + loc.getYaw() + "-" + loc.getWorld().getName();
    }

    public Location DbStringToLocation(String s) {
        String[] strings = s.split("-");
        int x, y, z;
        try {
            x = Integer.parseInt(strings[0]);
            y = Integer.parseInt(strings[1]);
            z = Integer.parseInt(strings[2]);
            float pitch, yaw;
            pitch = Float.parseFloat(strings[3]);
            yaw = Float.parseFloat(strings[4]);
            World w = Bukkit.getWorld(strings[5]);
            return new Location(w, x, y, z, yaw, pitch);
        } catch (NumberFormatException e) {
        }
        return null;
    }

    public String getLocationString(Location loc) {
        return loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
    }

    public void strikeLighting(Location location, boolean damage) {
        if (damage) {
            location.getWorld().strikeLightning(location);
            return;
        }
        location.getWorld().strikeLightningEffect(location);
    }


    public void strikeRandomLightning(Location loc, int radius, boolean damage) {
        Location loca = new Randomizer<Location>(this.drawCircle(loc, radius, 1, false, false, 0)).result();
        this.strikeLighting(loca, damage);
    }

}
