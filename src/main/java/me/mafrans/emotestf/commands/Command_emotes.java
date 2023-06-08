package me.mafrans.emotestf.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.mafrans.emotestf.util.EmoteLoader;
import me.mafrans.emotestf.util.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command_emotes implements CommandExecutor {
   public static List<CommandSender> mutedPlayers = new ArrayList();

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (args.length != 0 && !args[0].equalsIgnoreCase("list") && !args[0].equalsIgnoreCase("help")) {
         if (!args[0].equalsIgnoreCase("mute") && !args[0].equalsIgnoreCase("squelch")) {
            return false;
         } else {
            sender.sendMessage(
               mutedPlayers.contains(sender) ? ChatColor.GREEN + "You will now see all Emotes." : ChatColor.RED + "You will no longer see any Emotes."
            );
            if (mutedPlayers.contains(sender)) {
               mutedPlayers.remove(sender);
            } else {
               mutedPlayers.add(sender);
            }

            return true;
         }
      } else if (args.length > 2) {
         EmoteMeta emote = null;

         try {
            emote = EmoteLoader.getEmoteByCommand(args[1]);
            if (emote == null) {
               emote = EmoteLoader.getEmoteByName(args[1]);
            }
         } catch (IOException var8) {
            var8.printStackTrace();
         }

         if (emote != null) {
            sender.sendMessage(MessageUtils.colorize("&6Unknown Command: &c" + args[1]));
         }

         sender.sendMessage(MessageUtils.colorize("&e -- &6Emote Help: &c" + emote.getName() + "&e -- "));
         sender.sendMessage(MessageUtils.colorize("&6Description: &e" + emote.getDescription()));
         sender.sendMessage(MessageUtils.colorize("&6Usage: &e" + emote.getUsage()));
         sender.sendMessage(MessageUtils.colorize("&6Author: &e" + emote.getAuthor()));
         return true;
      } else {
         List<EmoteMeta> emoteList = EmoteLoader.getAllEmotes();
         sender.sendMessage(MessageUtils.colorize("&e ---- &6Emotes &e----"));

         for(EmoteMeta emote : emoteList) {
            sender.sendMessage(ChatColor.GOLD + "/" + emote.getName().toLowerCase() + ChatColor.WHITE + ": " + emote.getDescription());
         }

         return true;
      }
   }
}
