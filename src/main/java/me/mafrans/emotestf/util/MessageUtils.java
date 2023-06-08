package me.mafrans.emotestf.util;

import me.mafrans.emotestf.commands.Command_emotes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class MessageUtils {
    public static void globalEmoteMessage(final String string) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (!Command_emotes.mutedPlayers.contains(player)) {
                player.sendMessage(StringUtils.generateRandoms(string));
            }
        }
    }

    public static String colorize(final String string, final char symbol) {
        return ChatColor.translateAlternateColorCodes(symbol, string);
    }

    public static String colorize(final String string) {
        return colorize(string, '&');
    }
}
