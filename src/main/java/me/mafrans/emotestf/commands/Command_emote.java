package me.mafrans.emotestf.commands;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import me.mafrans.emotestf.EmotesTF;
import me.mafrans.emotestf.util.EmoteVariable;
import me.mafrans.emotestf.util.MessageUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Command_emote implements EmoteCommandExecutor {
   public static HashMap<CommandSender, Integer> cooldownPlayers = new HashMap<>();

   @Override
   public boolean onCommand(CommandSender sender, Command cmd, EmoteMeta emoteMeta, String label, String[] args) {
      if (cooldownPlayers.containsKey(sender)) {
         sender.sendMessage(
            ChatColor.GRAY
               + MessageUtils.colorize(
                  EmotesTF.plugin.getConfig().getString("emotes.cooldown-message").replace("%cooldown%", String.valueOf(cooldownPlayers.get(sender)))
               )
         );
         return true;
      } else {
         HashMap<EmoteVariable, String> variableMap = emoteMeta.getVariableMap();
         HashMap<String, String> toReplace = new HashMap<>();

         for(EmoteVariable var : variableMap.keySet()) {
            String custom = variableMap.get(var);
            switch(var) {
               case USER:
                  toReplace.put("%" + custom + "%", sender.getName());
                  break;
               case ARGUMENT_0:
                  if (args.length >= 1) {
                     toReplace.put("%" + custom + "%", args[0]);
                  }
                  break;
               case ARGUMENT_1:
                  if (args.length >= 2) {
                     toReplace.put("%" + custom + "%", args[1]);
                  }
                  break;
               case ARGUMENT_2:
                  if (args.length >= 3) {
                     toReplace.put("%" + custom + "%", args[2]);
                  }
                  break;
               case ARGUMENT_3:
                  if (args.length >= 4) {
                     toReplace.put("%" + custom + "%", args[3]);
                  }
                  break;
               case ARGUMENT_4:
                  if (args.length >= 5) {
                     toReplace.put("%" + custom + "%", args[4]);
                  }
                  break;
               case ARGUMENT_5:
                  if (args.length >= 6) {
                     toReplace.put("%" + custom + "%", args[5]);
                  }
                  break;
               case ARGUMENT_6:
                  if (args.length >= 7) {
                     toReplace.put("%" + custom + "%", args[6]);
                  }
                  break;
               case ARGUMENT_7:
                  if (args.length >= 8) {
                     toReplace.put("%" + custom + "%", args[7]);
                  }
                  break;
               case ARGUMENT_8:
                  if (args.length >= 9) {
                     toReplace.put("%" + custom + "%", args[8]);
                  }
                  break;
               case ARGUMENT_9:
                  if (args.length >= 10) {
                     toReplace.put("%" + custom + "%", args[9]);
                  }
                  break;
               case REST:
                  int last = 0;

                  for(EmoteVariable v : variableMap.keySet()) {
                     if (v.toString().startsWith("ARGUMENT") && Integer.parseInt(v.toString().substring(9, 10)) > last) {
                        last = Integer.parseInt(v.toString().substring(9, 10));
                     }
                  }

                  toReplace.put("%" + custom + "%", StringUtils.join(ArrayUtils.subarray(args, last + 1, args.length), " "));
            }
         }

         HashMap<List<EmoteVariable>, String[]> lines = emoteMeta.getLineMap();
         List<EmoteVariable> variableList = null;
         String[] stringArray = null;

         for(List<EmoteVariable> emoteVariableList : lines.keySet()) {
            if (!emoteVariableList.contains(EmoteVariable.USER)) {
               sender.sendMessage(ChatColor.RED + "This emote is badly formatted and cannot be ran.");
               sender.sendMessage(ChatColor.RED + "For more information please check the error message in the console.");
               MessageUtils.debugMessage(
                  Level.SEVERE, "The emote " + emoteMeta.getName() + " does not have 'USER' as a parameter in all lines and thus cannot be ran."
               );
               return true;
            }

            if (emoteVariableList.contains(EmoteVariable.REST) && args.length > emoteVariableList.size()) {
               variableList = emoteVariableList;
               stringArray = (String[])lines.get(emoteVariableList);
               break;
            }

            if (args.length == emoteVariableList.size() - 1) {
               variableList = emoteVariableList;
               stringArray = (String[])lines.get(emoteVariableList);
               break;
            }
         }

         if (variableList == null) {
            sender.sendMessage(emoteMeta.getUsage());
            return false;
         } else {
            for(String string : stringArray) {
               String message = string;

               for(String custom : toReplace.keySet()) {
                  message = message.replace(custom, toReplace.get(custom));
               }

               MessageUtils.globalEmoteMessage(MessageUtils.colorize(message));
            }

            cooldownPlayers.put(sender, EmotesTF.plugin.getConfig().getInt("emotes.cooldown"));
            return true;
         }
      }
   }
}
