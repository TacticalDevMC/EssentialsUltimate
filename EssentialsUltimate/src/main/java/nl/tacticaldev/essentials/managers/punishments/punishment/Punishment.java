package nl.tacticaldev.essentials.managers.punishments.punishment;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.essentialsutils.DateUtil;
import essentialsapi.utils.essentialsutils.DescParseTickFormat;

public class Punishment {

    protected String id;
    protected String reason;
    protected String banner;
    protected long created;

    public Punishment(final String id, final String reason, final String banner, final long created) {
        super();
        this.id = id;
        this.reason = reason;
        this.banner = banner;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public String getBanner() {
        return banner;
    }

    public long getCreated() {
        return created;
    }

    @Override
    public String toString() {
        String str = String.valueOf(this.getClass().getSimpleName()) + " ID:" + this.id + ((this.reason != null && !this.reason.isEmpty()) ? (", R:" + this.reason) : "") + ", B:" + this.banner + ", C:" + this.created;

        if (this instanceof Temporary) {
            final Temporary t = (Temporary) this;
            str = String.valueOf(str) + ", E:" + DateUtil.getTimeUntil(t.getExpires());
        }
        return str;
    }
}
