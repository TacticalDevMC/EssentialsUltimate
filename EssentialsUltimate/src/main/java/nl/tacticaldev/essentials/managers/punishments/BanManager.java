package nl.tacticaldev.essentials.managers.punishments;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Utils;
import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.database.sql.SQLManager;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.managers.punishments.ban.Ban;
import nl.tacticaldev.essentials.managers.punishments.ban.tempban.TempBan;
import nl.tacticaldev.essentials.managers.punishments.history.HistoryRecord;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BanManager {

    protected IEssentials ess;
    private HashMap<String, Ban> bans;
    private HashMap<String, TempBan> tempBans;
    private ArrayList<HistoryRecord> history;
    private HashMap<String, ArrayList<HistoryRecord>> personalHistory;
    private HashMap<String, HashSet<String>> iplookup;
    private TrieSet players;
    private HashMap<String, String> actualNames;

    public String defaultReason;
    private String appealMessage;

    private SQLManager sqlManager;

    public BanManager(final IEssentials ess) {
        super();
        this.ess = ess;
        sqlManager = new SQLManager(ess.getAPI().getDatabase());

        this.bans = new HashMap<>();
        this.tempBans = new HashMap<>();

        this.history = new ArrayList<>();
        this.personalHistory = new HashMap<>();
        this.iplookup = new HashMap<>();
        this.players = new TrieSet();
        this.actualNames = new HashMap<>();
        this.defaultReason = ess.getSettings().getDefaultReason();
        this.appealMessage = "";
        reload();
    }

    public String getAppealMessage() {
        return this.appealMessage;
    }

    public void setAppealMessage(final String msg) {
        this.appealMessage = ChatColor.translateAlternateColorCodes('&', msg);
    }

    public HashMap<String, Ban> getBans() {
        return bans;
    }

    public HashMap<String, TempBan> getTempBans() {
        return tempBans;
    }

    public HashMap<String, String> getPlayers() {
        return this.actualNames;
    }

    public HistoryRecord[] getHistory() {
        return this.history.toArray(new HistoryRecord[this.history.size()]);
    }

    public HistoryRecord[] getHistory(final String name) {
        final ArrayList<HistoryRecord> history = this.personalHistory.get(name);
        if (history != null) {
            return history.toArray(new HistoryRecord[history.size()]);
        }
        return new HistoryRecord[0];
    }

    public void addHistory(String name, String banner, final String message) {
        name = name.toLowerCase();
        banner = banner.toLowerCase();
        final HistoryRecord record = new HistoryRecord(name, banner, message);
        this.history.add(0, record);
        this.ess.getAPI().getDatabase().execute("INSERT INTO " + sqlManager.getTable("history") + " (created, message, name, banner) VALUES (?, ?, ?, ?)", System.currentTimeMillis(), message, name, banner);
        ArrayList<HistoryRecord> personal = this.personalHistory.get(name);
        if (personal == null) {
            personal = new ArrayList<>();
            this.personalHistory.put(name, personal);
        }
        personal.add(0, record);
        if (personal == null) {
            personal = new ArrayList<>();
            this.personalHistory.put(banner, personal);
        }
        personal.add(0, record);
    }

    public void reload() {
        this.bans.clear();
        this.tempBans.clear();
        this.players.clear();
        this.actualNames.clear();
        this.setAppealMessage(ess.getSettings().getDefaultAppealMessage());
        String query = "";
        Logger.INFO.log("Loading from db...");
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (!ess.getDBSettings().isReadOnly()) {
                ps = this.ess.getAPI().getDatabase().getConnection().prepareStatement("DELETE FROM " + sqlManager.getTable("bans") + " WHERE expires <> 0 AND expires < ?");
                ps.setLong(1, System.currentTimeMillis());
                ps.execute();
            }
            Logger.INFO.log("Loading bans");
            query = "SELECT * FROM " + sqlManager.getTable("bans");
            ps = this.ess.getAPI().getDatabase().getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                final String name = rs.getString("name");
                final String reason = rs.getString("reason");
                final String banner = rs.getString("banner");
                this.players.add(name);
                final long expires = rs.getLong("expires");
                final long time = rs.getLong("time");
                if (expires != 0L) {
                    final TempBan tb = new TempBan(name, reason, banner, time, expires);
                    this.tempBans.put(name.toLowerCase(), tb);
                } else {
                    final Ban ban = new Ban(name, reason, banner, time);
                    this.bans.put(name.toLowerCase(), ban);
                }
            }
        } catch (Exception e) {
            Logger.ERROR.log(e);
        }
        try {
            Logger.INFO.log("Loading history...");
            if (!ess.getDBSettings().isReadOnly() && ess.getSettings().getHistoryExpireyMinutes() > 0) {
                this.ess.getAPI().getDatabase().getConnection().prepareStatement("DELETE FROM " + sqlManager.getTable("history") + " WHERE created < " + (System.currentTimeMillis() - ess.getSettings().getHistoryExpireyMinutes() * 60000)).execute();
            }
            query = "SELECT * FROM " + sqlManager.getTable("history") + " ORDER BY created DESC";
            rs = ess.getAPI().getDatabase().getConnection().prepareStatement(query).executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                this.players.add(name);
                String banner = rs.getString("banner");
                final String message = rs.getString("message");
                final long created = rs.getLong("created");
                if (name == null) {
                    name = "unknown";
                }

                if (banner == null) {
                    banner = "unknown";
                }
                final HistoryRecord record = new HistoryRecord(name, banner, message, created);
                this.history.add(record);
                ArrayList<HistoryRecord> personal = this.personalHistory.get(name);
                if (personal == null) {
                    personal = new ArrayList<>();
                    this.personalHistory.put(name, personal);
                }
                personal.add(record);
                if (record.getName().equals(banner)) {
                    continue;
                }
                personal = this.personalHistory.get(banner);
                if (personal == null) {
                    personal = new ArrayList<HistoryRecord>();
                    this.personalHistory.put(banner, personal);
                }
                personal.add(record);
            }
        } catch (Exception e2) {
            Logger.ERROR.log(e2);
        }
    }

    public Ban getBan(String name) {
        name = name.toLowerCase();
        final Ban ban = this.bans.get(name);
        if (ban != null) {
            return ban;
        }
        final TempBan tempBan = this.tempBans.get(name);
        if (tempBan != null) {
            if (!tempBan.hasExpired()) {
                return tempBan;
            }
            this.tempBans.remove(name);
            this.ess.getAPI().getDatabase().execute("DELETE FROM " + sqlManager.getTable("bans") + " WHERE name=? AND expires <> 0", name);
        }
        return null;
    }

    public HashSet<String> getUsers(final String ip) {
        if (ip == null) {
            return null;
        }
        final HashSet<String> ips = this.iplookup.get(ip);
        if (ips == null) {
            return null;
        }
        return new HashSet<>(ips);
    }

    public void ban(String name, final String reason, String banner) {
        name = name.toLowerCase();
        banner = banner.toLowerCase();
        this.players.add(name);
        this.unban(name);
        final Ban ban = new Ban(name, reason, banner, System.currentTimeMillis());
        this.bans.put(name, ban);
        this.ess.getAPI().getDatabase().execute("INSERT INTO " + sqlManager.getTable("bans") + " (name, reason, banner, time) VALUES (?, ?, ?, ?)", name, reason, banner, System.currentTimeMillis());
        this.kick(name, Utils.replaceColor(ban.getKickMessage()));
    }

    public void tempban(String name, final String reason, String banner, final long expires) {
        name = name.toLowerCase();
        banner = banner.toLowerCase();
        this.players.add(name);
        this.unban(name);
        final TempBan ban = new TempBan(name, reason, banner, System.currentTimeMillis(), expires);
        this.tempBans.put(name, ban);
        this.ess.getAPI().getDatabase().execute("INSERT INTO " + sqlManager.getTable("bans") + " (name, reason, banner, time, expires) VALUES (?, ?, ?, ?, ?)", name, reason, banner, System.currentTimeMillis(), expires);
        this.kick(name, ban.getKickMessage());
    }

    public void unban(String name) {
        name = name.toLowerCase();
        final Ban ban = this.bans.get(name);
        final TempBan tBan = this.tempBans.get(name);
        if (ban != null) {
            this.bans.remove(name);
            this.ess.getAPI().getDatabase().execute("DELETE FROM " + sqlManager.getTable("bans") + " WHERE name = ?", name);
        }
        if (tBan != null) {
            this.tempBans.remove(name);
            if (ban == null) {
                this.ess.getAPI().getDatabase().execute("DELETE FROM " + sqlManager.getTable("bans") + " WHERE name = ?", name);
            }
        }
    }

    public void kick(final String user, final String msg) {
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                final Player p = Bukkit.getPlayerExact(user);
//                if (p != null && p.isOnline() && !BanManager.this.hasImmunity(user)) {
//                    p.kickPlayer(msg);
//                }

                if (p != null && p.isOnline()) {
                    p.kickPlayer(msg);
                }
            }
        };
        if (Bukkit.isPrimaryThread()) {
            r.run();
        } else {
            Bukkit.getScheduler().runTask((Plugin) ess, r);
        }
    }

    public String match(final String partial) {
        return this.match(partial, false);
    }

    public String match(String partial, final boolean excludeOnline) {
        partial = partial.toLowerCase();
//        final String ip = this.recentips.get(partial);
//        if (ip != null) {
//            return partial;
//        }
        if (!excludeOnline) {
            final Player p = Bukkit.getPlayer(partial);
            if (p != null) {
                return p.getName();
            }
        }
        final String nearestMap = this.players.nearestKey(partial);
        if (nearestMap != null) {
            return nearestMap;
        }
        return partial;
    }

    public HashSet<String> matchAll(String partial) {
        partial = partial.toLowerCase();
        return this.players.matches(partial);
    }

    public String convertName(final String lowercase) {
        return this.actualNames.get(lowercase.toLowerCase());
    }

    public void announce(final String s) {
        this.announce(s, false, null);
    }

    public void announce(String s, final boolean silent, final CommandSender sender) {
        if (silent) {
            s = Utils.replaceColor("&7[Silent] " + s);
            for (final Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("essentials.seesilent")) {
                    p.sendMessage(s);
                }
            }
            if (sender != null && !sender.hasPermission("essentials.seesilent")) {
                sender.sendMessage(s);
            }
        } else {
            for (final Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("essentials.seebroadcast")) {
                    p.sendMessage(s);
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage(s);
    }
}
