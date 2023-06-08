package me.mafrans.emotestf.commands;

import me.mafrans.emotestf.EmotesTF;
import me.mafrans.emotestf.util.EmoteVariable;
import me.mafrans.emotestf.util.MessageUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Command_emote implements EmoteCommandExecutor {
    private static final DecimalFormat TWO_DECIMALS = new DecimalFormat("#.##");
    public static final HashMap<UUID, Long> cooldownPlayers = new HashMap<>();

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final EmoteMeta emoteMeta, final String label, final String[] args) {
        if (sender instanceof final Player player) {
            if (cooldownPlayers.containsKey(player.getUniqueId())) {
                final long lastCommandTime = cooldownPlayers.get(player.getUniqueId());
                final long millisDifference = System.currentTimeMillis() - lastCommandTime;
                final long minimum = EmotesTF.plugin.getConfig().getLong("emotes.cooldown") * 1000;

                if (millisDifference < minimum) {
                    final String cooldown = TWO_DECIMALS.format((minimum - millisDifference) / 1000);

                    sender.sendMessage(ChatColor.GRAY + MessageUtils.colorize(
                            EmotesTF.plugin.getConfig().getString("emotes.cooldown-message")
                                    .replace("%cooldown%", cooldown)
                    ));
                    return true;
                }
            }

            cooldownPlayers.put(player.getUniqueId(), System.currentTimeMillis());
        }

        final HashMap<EmoteVariable, String> variableMap = emoteMeta.getVariableMap();
        final HashMap<String, String> toReplace = new HashMap<>();

        for (final EmoteVariable var : variableMap.keySet()) {
            final String custom = variableMap.get(var);
            switch (var) {
                case USER -> toReplace.put("%" + custom + "%", sender.getName());
                case ARGUMENT_0 -> {
                    if (args.length >= 1) {
                        toReplace.put("%" + custom + "%", args[0]);
                    }
                }
                case ARGUMENT_1 -> {
                    if (args.length >= 2) {
                        toReplace.put("%" + custom + "%", args[1]);
                    }
                }
                case ARGUMENT_2 -> {
                    if (args.length >= 3) {
                        toReplace.put("%" + custom + "%", args[2]);
                    }
                }
                case ARGUMENT_3 -> {
                    if (args.length >= 4) {
                        toReplace.put("%" + custom + "%", args[3]);
                    }
                }
                case ARGUMENT_4 -> {
                    if (args.length >= 5) {
                        toReplace.put("%" + custom + "%", args[4]);
                    }
                }
                case ARGUMENT_5 -> {
                    if (args.length >= 6) {
                        toReplace.put("%" + custom + "%", args[5]);
                    }
                }
                case ARGUMENT_6 -> {
                    if (args.length >= 7) {
                        toReplace.put("%" + custom + "%", args[6]);
                    }
                }
                case ARGUMENT_7 -> {
                    if (args.length >= 8) {
                        toReplace.put("%" + custom + "%", args[7]);
                    }
                }
                case ARGUMENT_8 -> {
                    if (args.length >= 9) {
                        toReplace.put("%" + custom + "%", args[8]);
                    }
                }
                case ARGUMENT_9 -> {
                    if (args.length >= 10) {
                        toReplace.put("%" + custom + "%", args[9]);
                    }
                }
                case REST -> {
                    int last = 0;
                    for (final EmoteVariable v : variableMap.keySet()) {
                        final int i = Integer.parseInt(v.toString().substring(9, 10));
                        if (v.toString().startsWith("ARGUMENT") && i > last) {
                            last = i;
                        }
                    }
                    toReplace.put("%" + custom + "%", StringUtils.join(ArrayUtils.subarray(args, last + 1, args.length), " "));
                }
            }
        }

        final HashMap<List<EmoteVariable>, String[]> lines = emoteMeta.getLineMap();
        List<EmoteVariable> variableList = null;
        String[] stringArray = null;

        for (final List<EmoteVariable> emoteVariableList : lines.keySet()) {
            if (!emoteVariableList.contains(EmoteVariable.USER)) {
                sender.sendMessage(ChatColor.RED + "This emote is badly formatted and cannot be ran.");
                sender.sendMessage(ChatColor.RED + "The emote " + emoteMeta.getName() + " does not have 'USER' as a parameter in all lines and thus cannot be ran.");
                return true;
            }

            if (emoteVariableList.contains(EmoteVariable.REST) && args.length > emoteVariableList.size()) {
                variableList = emoteVariableList;
                stringArray = lines.get(emoteVariableList);
                break;
            }

            if (args.length == emoteVariableList.size() - 1) {
                variableList = emoteVariableList;
                stringArray = lines.get(emoteVariableList);
                break;
            }
        }

        if (variableList == null) {
            sender.sendMessage(emoteMeta.getUsage());
            return false;
        }

        for (final String string : stringArray) {
            String message = string;

            for (final String custom : toReplace.keySet()) {
                message = message.replace(custom, toReplace.get(custom));
            }

            MessageUtils.globalEmoteMessage(MessageUtils.colorize(message));
        }

        return true;
    }
}
