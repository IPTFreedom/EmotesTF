package me.mafrans.emotestf.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Command_notifyme implements CommandExecutor {
    public static List<Player> noNotify = new ArrayList<>();

    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String s, final String[] args) {
        if (!(sender instanceof Player pSender)) {
            sender.sendMessage("This command can only be used in game.");
            return true;
        } else {
            if (args.length == 0) {
                if (noNotify.contains(pSender)) {
                    noNotify.remove(pSender);
                    sender.sendMessage(ChatColor.GRAY + "Notification sounds are now enabled for " + pSender.getName() + ".");
                } else {
                    noNotify.add(pSender);
                    sender.sendMessage(ChatColor.GRAY + "Notification sounds are now disabled for " + pSender.getName() + ".");
                }
                return true;
            } else if (!args[0].equalsIgnoreCase("true") && !args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("enabled")) {
                if (!args[0].equalsIgnoreCase("false") && !args[0].equalsIgnoreCase("off") && !args[0].equalsIgnoreCase("disabled")) {
                    return false;
                } else {
                    if (!noNotify.contains(pSender)) {
                        noNotify.add(pSender);
                    }

                    sender.sendMessage(ChatColor.GRAY + "Notification sounds are now disabled for " + pSender.getName() + ".");
                    return true;
                }
            } else {
                noNotify.remove(pSender);

                sender.sendMessage(ChatColor.GRAY + "Notification sounds are now enabled for " + pSender.getName() + ".");
                return true;
            }
        }
    }
}
