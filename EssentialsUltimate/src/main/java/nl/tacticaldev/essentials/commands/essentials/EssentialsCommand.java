package nl.tacticaldev.essentials.commands.essentials;

import essentialsapi.utils.essentialsutils.NumberUtil;
import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CommandModule;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static essentialsapi.utils.Utils.replaceColor;

public class EssentialsCommand extends CoreCommand {

    public EssentialsCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {
        this.registerSub(new SubCommand("reload", "essentials.reload", "Reload the plugin") {
            @Override
            public void execute(String[] args) throws CoreException {
                ess.reload();

                user.sendMessage(replaceColor("&6EssentialsUltimate reloaded Team City " + ess.getDescription().getVersion()));
            }
        });

        this.registerSub(new SubCommand("help", "essentials.help", "Get the help list", true) {
            @Override
            public void execute(String[] args) throws CoreException {
                if (getArgs().length == 0) {
                    sendHelpPage(getSender(), 1);
                } else {
                    if (NumberUtil.isInt(getArgs()[0])) {
                        sendHelpPage(getSender(), Integer.parseInt(getArgs()[0]));
                    } else {
                        getSender().sendMessage(settings.getPrefix() + " &cGelieve een getal invullen.");
                    }
                }
            }
        });
    }

    @Override
    public void execute() throws Exception {

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return getSuggestions(args[0], "help", "reload");
        }
        return new ArrayList<>();
    }

    public void sendHelpPage(CommandSender sender, int page) {
        if (page > 5) {
            sender.sendMessage(settings.getPrefix() + "Onbekende pagina!");
            return;
        }
        int pageNum = (5 * page) - 5;
        int maxHelp = 5 * page;

        sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");
        for (int i = pageNum; i < maxHelp; i++) {
            if (i < CommandModule.getInstance().getCommand().size()) {
                CoreCommand c = CommandModule.getInstance().getCommand().get(i);
                if (!sender.hasPermission(c.getPermission())) continue;
                if (c.getUsage() == "") {
                    sender.sendMessage(ChatColor.GOLD + "/" + c.getName() + " " + ChatColor.LIGHT_PURPLE + c.getInfo());
                } else {
                    sender.sendMessage(ChatColor.GOLD + "/" + c.getName() + " " + ChatColor.GREEN + c.getUsage() + " " + ChatColor.LIGHT_PURPLE + c.getInfo());
                }
            } else {
                sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");
                return;
            }
        }

        int maxPages = CommandModule.getInstance().getCommand().size() / 5;
        int newPage = page + 1;

        if (newPage <= maxPages) {
            sender.sendMessage(ChatColor.BLUE + "Kijk verder op pagina " + newPage);
        }
        sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");
    }
}
