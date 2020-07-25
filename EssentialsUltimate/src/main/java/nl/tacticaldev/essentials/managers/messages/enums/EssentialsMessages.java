package nl.tacticaldev.essentials.managers.messages.enums;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import nl.tacticaldev.essentials.managers.messages.MessageManager;
import org.bukkit.command.CommandSender;

import static essentialsapi.utils.Utils.replaceColor;

@AllArgsConstructor
@Getter
public enum EssentialsMessages {

    // GENERAL
    PLAYER_NOT_FOUND_IN_DATABASE("commands.general.playerNotFoundInDatabase"),
    PLAYER_ERROR("commands.general.playerError"),
    IN_COOLDOWN("commands.general.inCooldown"),

    // TELEPORT
    TELEPORT_ARGS("commands.teleport.tp.args"),
    TELEPORT_TELEPORTED_TO_OFFLINE("commands.teleport.tp.teleportedToOffline"),
    TELEPORT_TELEPORTED_TO_ONLINE("commands.teleport.tp.teleportedToOnline"),
    TELEPORT_OFFLINE_ARGS("commands.teleport.tpoffline.args"),
    TELEPORT_OFFLINE_PLAYER_IS_ONLINE("commands.teleport.tpoffline.playerIsOnline"),
    TELEPORT_OFFLINE_TELEPORTED("commands.teleport.tpoffline.teleportedToOffline"),

    // SPAWN
    SETSPAWN_ARGS("commands.setspawn.args"),
    SETSPAWN_SPAWN_ALREADY_EXISTS("commands.setspawn.spawnAlreadyExists"),
    SETSPAWN_SPAWNSET("commands.setspawn.spawnSet"),
    SPAWNS_SPAWN_NOT_FOUND("commands.spawns.noSpawnsFound"),
    SPAWNS_LIST_FORMAT("commands.spawns.listFormat"),
    SPAWN_TELEPORTED_TO_DEFAULT("commands.spawn.teleportedToDefault"),
    SPAWN_DEFAULT_SPAWN_CAN_NOT_BE_FOUND("commands.spawn.defaultSpawnNotFound"),
    SPAWN_SPAWN_NOT_EXISTS("commands.spawn.spawnNotExists"),
    SPAWN_TELEPORTED("commands.spawn.teleported"),
    DELETESPAWN_ARGS("commands.deletespawn.args"),
    DELETESPAWN_SPAWN_NOT_FOUND("commands.deletespawn.spawnNotFound"),
    DELETESPAWN_SPAWN_DELETED("commands.deletespawn.spawnDeleted"),

    // PUNISHMENTS
    BAN_ARGS("commands.ban.args"),
    BAN_NO_PLAYER_GIVEN("commands.ban.noPlayerGiven"),
    BAN_PLAYER_ALREADY_BANNED("commands.ban.playerAlreadyBanned"),
    TEMPBAN_ARGS("commands.tempban.args"),
    TEMPBAN_NO_PLAYER_GIVEN("commands.tempban.noPlayerGiven"),
    TEMPBAN_BAN_TIME_TO_LONG("commands.tempban.banTimeToLong"),
    TEMPBAN_TEMPBAN_SHORTER_THAN_LAST("commands.tempban.tempbanShorterThanLast"),
    IPBAN_ARSG("commands.ipban.args"),
    IPBAN_NO_IP_RECORD_FOUND("commands.ipban.noIPRecordFound"),
    IPBAN_IP_ALREADY_BANNED("commands.ipban.ipAlreadyBanned"),

    // HEAL / FEED
    HEAL_ARGS("commands.heal.args"),
    HEAL_PLAYER_HEALED("commands.heal.playerHealed"),
    HEAL_TARGET_HEALED("commands.heal.targetHealed"),

    // INVENTORY
    CLEAR_ARGS("commands.clear.args"),
    CLEAR_INVENTORY_CLEARED("commands.clear.inventoryCleared"),

    // WARP
    SETWARP_INVALID_WARP_NAME("commands.setwarp.invalidWarpName"),
    SETWARP_WARP_SET("commands.setwarp.warpSet"),
    WARP_NO_LIST_PERMS("commands.warp.noListPerms"),
    WARP_NO_WARPS_DEFINED("commands.warp.noWarpsDefined"),
    WARP_WARPS_COUNT("commands.warp.noWarpsDefined"),
    WARP_WARPS_LIST("commands.warp.warpList"),
    WARP_WARPS("commands.warp.warps"),
    WARP_NO_WARP_PERMISSION("commands.warp.noWarpPermission"),
    WARP_WARPING_TO("commands.warp.warpingTo"),

    // SIGNS
    SIGN_HEAL_HEALED("signs.heal.healed"),
    SIGN_ON_COOLDOWN("signs.onCooldown"),

    // MENUS
    MENUS_LANG_LANGSELECTED("menus.lang.langSelected");

    private final String path;

    @Override
    public String toString() {
        IEssentials ess = Essentials.getInstance();
        ISettings settings = ess.getSettings();

        MessageManager messageManager = Essentials.getInstance().getMessageManager();

//        if (!messageManager.getConfig("nl").getFile().exists()) {
//            return "No lang file 'NL' found. If the file is'nt been created if you reload or start the server, use the command: '/lang createfile nl'.";
//        } else if (!messageManager.getConfig("en").getFile().exists()) {
//            return "No lang file 'EN' found. If the file is'nt been created if you reload or start the server, use the command: '/lang createfile en'.";
//        }

        switch (settings.getLanguage()) {
            case "NL":
                return replaceColor(messageManager.getConfig("nl").getConfiguration().getString(path)).replace("%prefix%", settings.getPrefix());
            case "EN":
                return replaceColor(messageManager.getConfig("en").getConfiguration().getString(path)).replace("%prefix%", settings.getPrefix());
            default:
                return "No Language found!";
        }
    }

    public void send(CommandSender sender) {
        sender.sendMessage(toString());
    }

    public void send(CommandSender sender, String replacement) {
        sender.sendMessage(toString().replace("%s", replaceColor(replacement)).replace("%prefix%", Essentials.getInstance().getSettings().getPrefix()));
    }

//    public void send(CommandSender sender, String replacePath, String replaceTo) {
//        sender.sendMessage(toString().replace(replacePath, Utils.replaceColor(replaceTo)).replace("%prefix%", Essentials.getInstance().getSettings().getPrefix()));
//    }

    public void send(CommandSender sender, Object... object) {
        sender.sendMessage(String.format(toString(), object));
    }

}
