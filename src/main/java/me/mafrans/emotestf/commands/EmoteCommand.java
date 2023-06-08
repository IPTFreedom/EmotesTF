package me.mafrans.emotestf.commands;

import java.io.IOException;
import java.util.ArrayList;
import me.mafrans.emotestf.util.EmoteLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EmoteCommand extends Command {
   private EmoteCommandExecutor exe = null;

   protected EmoteCommand(String name) {
      super(name);
   }

   protected EmoteCommand(String name, String description, String usageMessage) {
      super(name, description, usageMessage, new ArrayList());
   }

   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
      if (this.exe != null) {
         try {
            this.exe.onCommand(sender, this, EmoteLoader.getEmoteByCommand(commandLabel), commandLabel, args);
         } catch (IOException var5) {
            var5.printStackTrace();
         }
      }

      return false;
   }

   public void setExecutor(EmoteCommandExecutor exe) {
      this.exe = exe;
   }
}
