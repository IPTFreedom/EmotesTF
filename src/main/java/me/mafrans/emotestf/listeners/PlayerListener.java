package me.mafrans.emotestf.listeners;

import java.util.logging.Level;
import me.mafrans.emotestf.EmotesTF;
import me.mafrans.emotestf.commands.Command_notifyme;
import me.mafrans.emotestf.util.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerListener implements Listener {
   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onChat(PlayerChatEvent e) {
      FileConfiguration config = EmotesTF.plugin.getConfig();
      if (config.getBoolean("mentions.enabled")) {
         Player player = e.getPlayer();
         String message = e.getMessage();
         if (message.contains("@")) {
            for(Player p : Bukkit.getOnlinePlayers()) {
               MessageUtils.debugMessage(Level.INFO, "Checking " + p.getName());
               if (message.toLowerCase().contains("@" + p.getName().toLowerCase())
                  && (!EmotesTF.plugin.TFMBridge.isEnabled() || !EmotesTF.plugin.TFMBridge.getVanishedPlayers().contains(p))) {
                  if (config.getBoolean("mentions.use-nicknames")) {
                     message = message.replaceAll(
                        "(?i)@" + p.getName(),
                        ChatColor.LIGHT_PURPLE
                           + "@"
                           + ChatColor.stripColor(p.getDisplayName()).replaceFirst(config.getString("mentions.nickname-prefix"), "")
                           + ChatColor.RESET
                     );
                  } else {
                     message = message.replaceAll("(?i)@" + p.getName(), ChatColor.LIGHT_PURPLE + "@" + p.getName() + ChatColor.RESET);
                  }

                  if (!Command_notifyme.noNotify.contains(p)) {
                     p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HARP, 2.0F, 1.68F);
                  }
               }
            }

            e.setMessage(message);
         }
      }
   }
}
