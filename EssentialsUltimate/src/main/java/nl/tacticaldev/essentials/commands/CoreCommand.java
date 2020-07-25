package nl.tacticaldev.essentials.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

import essentialsapi.utils.exception.NotEnoughArgumentsException;
import net.md_5.bungee.api.ChatColor;
import nl.tacticaldev.essentials.Essentials;
import essentialsapi.utils.exception.CoreCommandException;
import essentialsapi.utils.exception.CoreException;
import essentialsapi.utils.exception.UnkownPlayerException;
import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import static essentialsapi.utils.Utils.replaceColor;

public abstract class CoreCommand implements CommandExecutor, TabCompleter {

    private ArrayList<SubCommand> subCommands = new ArrayList<SubCommand>();
    private String name;
    private String permission;
    private int lenght;
    private String[] args;
    private CommandSender sender;
    private String info;
    private String usage;
    private boolean sub;
    private boolean allowConsole;

    public static IEssentials ess = Essentials.getInstance();
    public static ISettings settings = ess.getSettings();
    public static EssentialsPlayer user;

    public boolean allowSub() {
        return sub;
    }

    public boolean allowConsole() {
        return allowConsole;
    }

    public void setAllowConsole(boolean allow) {
        this.allowConsole = allow;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public int getLenght() {
        return lenght;
    }

    public String[] getArgs() {
        return args;
    }

    public String getInfo() {
        return info;
    }

    public String getUsage() {
        return usage;
    }

    public CoreCommand(String name, String permission, String info, String usage, boolean sub,
                       boolean allowConsole) {
        this.name = name;
        this.permission = permission;
        this.info = info;
        this.usage = usage;
        CommandModule.getInstance().getCommand().add(this);
        this.sub = sub;
        this.allowConsole = allowConsole;
        if (sub) {
            initHelp();
            init();
        }
    }

    public abstract void init();

    public abstract void execute() throws Exception;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            if (!sender.hasPermission(this.permission)) {
                sender.sendMessage(Essentials.getInstance().getSettings().getNoPermissions().replace("%permission%", this.permission));
                return false;
            }
            if (!allowConsole) {
                if (sender instanceof ConsoleCommandSender) {
                    getSender().sendMessage(replaceColor(Essentials.getInstance().getSettings().getPrefix() + "&cDit commando is alleen beschikbaar voor spelers!"));
                    return false;
                }
            }
            this.args = args;
            this.sender = sender;
            if (allowConsole) {
                user = new EssentialsPlayer(getSender().getName());
            } else {
                user = new EssentialsPlayer(getPlayer());
            }

            if (sub) {
                SubCommand subC = getSub(getArgs()[0]);
                if (subC == null) {
                    getSub("help").execute(new String[]{"help", "1"});
                } else {
                    if (!sender.hasPermission(subC.getPermission())) {
                        sender.sendMessage(Essentials.getInstance().getSettings().getNoPermissions().replace("%permission%", this.permission));
                        return false;
                    }
                    if (!subC.allowConsole()) {
                        if (sender instanceof ConsoleCommandSender) {
                            getSender().sendMessage(replaceColor(Essentials.getInstance().getSettings().getPrefix() + "&cDit commando is alleen beschikbaar voor spelers!"));
                            return false;
                        }
                    }
                    subC.execute(args);
                    Essentials.getInstance().getMetrics().markCommand(cmd.getName(), true);
                }
            } else {
                try {
                    this.execute();
                    Essentials.getInstance().getMetrics().markCommand(cmd.getName(), true);
                } catch (ArrayIndexOutOfBoundsException e) {
                    sender.sendMessage(ChatColor.GOLD + "/" + this.name + " " + ChatColor.GREEN + this.getInfo() + "");
                } catch (UnkownPlayerException e) {
                    getSender().sendMessage(replaceColor(Essentials.getInstance().getSettings().getPrefix() + "&cOnbekende speler!"));
                } catch (Exception e) {
                    getSender().sendMessage(replaceColor(Essentials.getInstance().getSettings().getPrefix() + "&cEr ging iets fout!"));
                    Logger.WARNING.log("Discovered Command Exception!");
                    Logger.ERROR.log("---------------------------");
                    Logger.ERROR.log("Exception: " + e.toString());
                    for (StackTraceElement s : e.getStackTrace()) {
                        Logger.ERROR.log(s.getClassName() + " [" + s.getLineNumber() + "/" + s.getMethodName()
                                + "] [" + s.getFileName() + "]");
                    }
                    Logger.ERROR.log("---------------------------");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (args.length == 0) {
                try {
                    if (sub) {
                        getSub("help").execute(new String[]{"help", "1"});
                    }
                } catch (CoreException e1) {
                }
            }
            if (sub) {
                SubCommand subC = null;
                try {
                    subC = getSub(args[0]);
                } catch (ArrayIndexOutOfBoundsException ex) {

                }
                if (subC == null) return false;
                sender.sendMessage(ChatColor.GOLD + "/" + this.name + " " + ChatColor.GREEN + subC.getMainCommand() + " "
                        + ChatColor.LIGHT_PURPLE + subC.getInfo());
            } else {
                if (this.getUsage() == "") {
                    sender.sendMessage(
                            ChatColor.GOLD + "/" + this.getName() + " " + ChatColor.LIGHT_PURPLE + this.getInfo());
                } else {
                    sender.sendMessage(ChatColor.GOLD + "/" + this.getName() + " " + ChatColor.GREEN + this.getUsage()
                            + " " + ChatColor.LIGHT_PURPLE + this.getInfo());
                }
            }
        } catch (Exception e) {
            getSender().sendMessage(replaceColor(Essentials.getInstance().getSettings().getPrefix() + "&cEr ging iets fout!"));
            Logger.WARNING.log("Discovered Command Exception!");
            Logger.ERROR.log("---------------------------");
            Logger.ERROR.log("Exception: " + e.toString());
            for (StackTraceElement s : e.getStackTrace()) {
                Logger.ERROR.log(s.getClassName() + " [" + s.getLineNumber() + "/" + s.getMethodName()
                        + "] [" + s.getFileName() + "]");
            }
            Logger.ERROR.log("---------------------------");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    public void registerCommand() {
        Essentials.getInstance().getServer().getPluginCommand(name).setExecutor(this);
        Essentials.getInstance().getServer().getPluginCommand(name).setTabCompleter(this);
    }

    public void registerSub(SubCommand sub) {
        this.subCommands.add(sub);
    }

    private SubCommand getSub(String command) {
        for (SubCommand sub : subCommands) {
            for (String c : sub.commands) {
                if (c.equalsIgnoreCase(command)) {
                    return sub;
                }
            }
        }
        return null;
    }

    public Player getPlayer() {
        if (!allowConsole) {
            return (Player) sender;
        }
        try {
            throw new CoreCommandException("getPlayer() is illegal for this command!");
        } catch (CoreCommandException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CommandSender getSender() {
        if (allowConsole) {
            return sender;
        }
        return getPlayer();
    }

    public List<String> getSuggestions(String argument, String... array) {
        argument = argument.toLowerCase();
        List<String> suggestions = new ArrayList<String>();
        for (String suggestion : array) {
            if (suggestion.toLowerCase().startsWith(argument)) {
                suggestions.add(suggestion);
            }
        }
        return suggestions;
    }

    public List<String> getPlayers(String[] args, Integer argsInt) {
        ArrayList<String> suggestions = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase().startsWith(args[argsInt].toLowerCase())) {
                suggestions.add(player.getName());
            }
        }
        return suggestions;
    }

    public List<String> getOfflinePlayers(String[] args, Integer argsInt) {
        ArrayList<String> suggestions = new ArrayList<>();
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName().toLowerCase().startsWith(args[argsInt].toLowerCase())) {
                suggestions.add(player.getName());
            }
        }
        return suggestions;
    }

    public void showError(CommandSender sender, Throwable throwable, String commandLabel) {
        sender.sendMessage("Error: " + throwable.getMessage());
        if (settings.isDebug()) {
            Logger.ERROR.log("Error calling the command /" + commandLabel);
            throwable.printStackTrace();
        }
    }

    public CompletableFuture<Boolean> getNewExceptionFuture(CommandSender sender, String commandLabel) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.exceptionally(e -> {
            showError(sender, e, commandLabel);
            return false;
        });
        return future;
    }

    private void initHelp() {
        this.registerSub(new SubCommand("help", this.permission, "Help commando") {
            @Override
            public void execute(String[] args) {
                try {
                    try {
                        sendHelpPage(sender, Integer.parseInt(getArgs()[1]));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        sendHelpPage(sender, 1);
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(replaceColor(Essentials.getInstance().getSettings().getPrefix() + "&cOnbekende pagina!"));
                }
            }
        });
    }

    public void sendHelpPage(CommandSender sender, int page) {
        if (page > 5) {
            sender.sendMessage(replaceColor(Essentials.getInstance().getSettings().getPrefix() + "&cOnbekende pagina!"));
            return;
        }
        int pageNum = (5 * page) - 5;
        int maxHelp = 5 * page;
        sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");
        for (int i = pageNum; i < maxHelp; i++) {
            if (i < this.subCommands.size()) {
                SubCommand c = this.subCommands.get(i);
                if (!sender.hasPermission(c.getPermission()))
                    continue;
                if (c.getMainCommand().equalsIgnoreCase("help"))
                    continue;
                sender.sendMessage(ChatColor.GOLD + "/" + this.name + " " + ChatColor.GREEN + c.getMainCommand() + " "
                        + ChatColor.LIGHT_PURPLE + c.getInfo());

            } else {
                sender.sendMessage(
                        ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");
                return;
            }
        }

        sender.sendMessage(ChatColor.BLUE + "Gebruik /" + this.name + " help " + (page + 1) + " om verder te kijken!");
        sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");

    }
}