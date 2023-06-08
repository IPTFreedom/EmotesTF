package me.mafrans.emotestf.commands;

import me.mafrans.emotestf.EmotesTF;
import me.mafrans.emotestf.util.EmoteLoader;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public final class CommandLoader {
    public static void loadCommand(final EmoteMeta emoteMeta) throws NoSuchFieldException, IllegalAccessException {
        final Field bukkitCommandMap = EmotesTF.plugin.getServer().getPluginManager().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        final CommandMap commandMap = (CommandMap) bukkitCommandMap.get(EmotesTF.plugin.getServer().getPluginManager());
        final EmoteCommand command = new EmoteCommand(emoteMeta.getName(), emoteMeta.getDescription(), emoteMeta.getUsage());
        command.setExecutor(new Command_emote());
        commandMap.register(emoteMeta.getCommand(), command);
    }

    public static void loadAllCommands() throws NoSuchFieldException, IllegalAccessException {
        for (final EmoteMeta emoteMeta : EmoteLoader.getAllEmotes()) {
            loadCommand(emoteMeta);
        }
    }
}
