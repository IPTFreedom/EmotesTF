package me.mafrans.emotestf.commands;

import java.lang.reflect.Field;
import me.mafrans.emotestf.EmotesTF;
import me.mafrans.emotestf.util.EmoteLoader;
import org.bukkit.command.CommandMap;

public class CommandLoader {
   public static void loadCommand(EmoteMeta emoteMeta) throws NoSuchFieldException, IllegalAccessException {
      Field bukkitCommandMap = EmotesTF.plugin.getServer().getPluginManager().getClass().getDeclaredField("commandMap");
      bukkitCommandMap.setAccessible(true);
      CommandMap commandMap = (CommandMap)bukkitCommandMap.get(EmotesTF.plugin.getServer().getPluginManager());
      EmoteCommand command = new EmoteCommand(emoteMeta.getName(), emoteMeta.getDescription(), emoteMeta.getUsage());
      command.setExecutor(new Command_emote());
      commandMap.register(emoteMeta.getCommand(), command);
   }

   public static void loadAllCommands() throws NoSuchFieldException, IllegalAccessException {
      for(EmoteMeta emoteMeta : EmoteLoader.getAllEmotes()) {
         loadCommand(emoteMeta);
      }
   }
}
