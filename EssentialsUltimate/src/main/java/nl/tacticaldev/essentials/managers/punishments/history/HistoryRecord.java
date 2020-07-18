package nl.tacticaldev.essentials.managers.punishments.history;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

public class HistoryRecord {

    private String name;
    private String banner;
    private String message;
    private long created;

    public HistoryRecord(final String name, final String banner, final String message) {
        this(name, banner, message, System.currentTimeMillis());
    }

    public HistoryRecord(final String name, final String banner, final String message, final long created) {
        super();
        this.message = message;
        this.created = created;
        this.banner = banner;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBanner() {
        return banner;
    }

    public String getMessage() {
        return message;
    }

    public long getCreated() {
        return created;
    }

    public String toString() {
        return this.message;
    }

}
