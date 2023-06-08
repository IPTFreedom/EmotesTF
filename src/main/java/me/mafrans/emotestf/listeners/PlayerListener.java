package me.mafrans.emotestf.listeners;

import me.mafrans.emotestf.EmotesTF;
import me.mafrans.emotestf.commands.Command_emote;
import me.mafrans.emotestf.commands.Command_notifyme;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerListener implements Listener {
    private static final Pattern MENTION_PATTERN = Pattern.compile("@([a-zA-Z0-9_]+)");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final @SuppressWarnings("deprecation") AsyncPlayerChatEvent e) {
        if (!EmotesTF.plugin.getConfig().getBoolean("mentions.enabled")) return;

        String message = e.getMessage();
        if (!message.contains("@")) return;

        final Matcher matcher = MENTION_PATTERN.matcher(message);

        final List<Player> alreadyPinged = new ArrayList<>();
        while (matcher.find()) {
            final Player player = Bukkit.getPlayer(matcher.group(1));
            if (player == null) continue;
            if (EmotesTF.plugin.TFMBridge.isVanished(player.getName())) continue;
            message = message.replace(matcher.group(0), ChatColor.LIGHT_PURPLE + matcher.group(0) + ChatColor.RESET);

            if (alreadyPinged.contains(player) || Command_notifyme.noNotify.contains(player)) continue;
            alreadyPinged.add(player);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 2.0F, 1.68F);
        }

        e.setMessage(message);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        Command_emote.cooldownPlayers.remove(e.getPlayer().getUniqueId());
    }
}
