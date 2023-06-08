package me.mafrans.emotestf.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface EmoteCommandExecutor {
    boolean onCommand(final CommandSender var1, final Command var2, final EmoteMeta var3, final String var4, final String[] var5);
}
