package me.mafrans.emotestf.util;

import java.util.logging.Level;
import me.mafrans.emotestf.EmotesTF;
import me.mafrans.emotestf.commands.Command_emotes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {
   public static void globalMessage(String string) {
      for(Player player : Bukkit.getOnlinePlayers()) {
         player.sendMessage(string);
      }
   }

   public static void globalMessage(String[] strings) {
      for(Player player : Bukkit.getOnlinePlayers()) {
         for(String string : strings) {
            player.sendMessage(string);
         }
      }
   }

   public static void globalEmoteMessage(String string) {
      for(Player player : Bukkit.getOnlinePlayers()) {
         if (!Command_emotes.mutedPlayers.contains(player)) {
            player.sendMessage(StringUtils.generateRandoms(string));
         }
      }
   }

   public static void globalEmoteMessage(String[] strings) {
      for(Player player : Bukkit.getOnlinePlayers()) {
         if (!Command_emotes.mutedPlayers.contains(player)) {
            for(String string : strings) {
               player.sendMessage(StringUtils.generateRandoms(string));
            }
         }
      }
   }

   public static void debugMessage(Level level, String message) {
      if (EmotesTF.plugin.getConfig().getBoolean("debug")) {
         EmotesTF.plugin.getLogger().log(level, message);
      }
   }

   public static String colorize(String string, char symbol) {
      return ChatColor.translateAlternateColorCodes(symbol, string);
   }

   public static String colorize(String string) {
      return colorize(string, '&');
   }
}
