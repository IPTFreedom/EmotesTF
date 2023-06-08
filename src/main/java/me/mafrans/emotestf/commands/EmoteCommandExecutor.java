package me.mafrans.emotestf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface EmoteCommandExecutor {
   boolean onCommand(CommandSender var1, Command var2, EmoteMeta var3, String var4, String[] var5);
}
