package me.mafrans.emotestf.commands;

import me.mafrans.emotestf.util.EmoteLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class EmoteCommand extends Command {
    private EmoteCommandExecutor exe = null;

    protected EmoteCommand(final String name, final String description, final String usageMessage) {
        super(name, description, usageMessage, new ArrayList<>());
    }

    public boolean execute(final @NotNull CommandSender sender, final @NotNull String commandLabel, final String[] args) {
        if (this.exe != null) {
            try {
                this.exe.onCommand(sender, this, EmoteLoader.getEmoteByCommand(commandLabel), commandLabel, args);
            } catch (IOException var5) {
                var5.printStackTrace();
            }
        }

        return false;
    }

    public void setExecutor(final EmoteCommandExecutor exe) {
        this.exe = exe;
    }
}
