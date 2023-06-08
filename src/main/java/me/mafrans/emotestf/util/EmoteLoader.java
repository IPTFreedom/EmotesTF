package me.mafrans.emotestf.util;

import me.mafrans.emotestf.EmotesTF;
import me.mafrans.emotestf.commands.EmoteMeta;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class EmoteLoader {
    private static List<EmoteMeta> cachedEmoteMetas = new ArrayList<>();
    private static final HashMap<String, URL> defaultEmoteJSON = new HashMap<>();
    private static final HashMap<String, Object[]> otherDefaults = new HashMap<>();

    public static EmoteMeta getEmoteByName(final String name) throws IOException {
        for (final EmoteMeta emoteMeta : cachedEmoteMetas) {
            if (emoteMeta.getName().equalsIgnoreCase(name)) {
                return emoteMeta;
            }
        }

        return null;
    }

    public static EmoteMeta getEmoteByCommand(final String command) throws IOException {
        for (final EmoteMeta emoteMeta : cachedEmoteMetas) {
            if (emoteMeta.getCommand().equalsIgnoreCase(command)) {
                return emoteMeta;
            }
        }

        return null;
    }

    public static List<EmoteMeta> getAllEmotes() {
        return cachedEmoteMetas;
    }

    public static void loadEmotes(final File dir) throws IOException {
        final List<EmoteMeta> emoteMetaList = new ArrayList<>();
        final File[] fileArray = dir.listFiles();

        for (final File file: fileArray) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                final EmoteMeta emoteMeta = new EmoteMeta();
                final String stringJson = FileUtils.readFile(file);
                final JSONObject json = new JSONObject(stringJson);
                emoteMeta.setName(json.getString("name"));
                emoteMeta.setCommand(json.getString("command"));
                emoteMeta.setDescription(json.getString("description"));
                emoteMeta.setAuthor(json.getString("author"));
                emoteMeta.setUsage(json.getString("usage"));
                final HashMap<List<EmoteVariable>, String[]> lines = new HashMap<>();
                final JSONObject jsonLines = json.getJSONObject("lines");

                for (final String key : jsonLines.keySet()) {
                    final JSONArray array = jsonLines.getJSONArray(key);
                    final String[] variableNames = key.split(",");
                    final List<EmoteVariable> variables = new ArrayList<>();

                    for (final String variableName : variableNames) {
                        variables.add(EmoteVariable.valueOf(variableName.toUpperCase()));
                    }

                    lines.put(variables, array.toList().toArray(String[]::new));
                }

                emoteMeta.setLineMap(lines);
                final HashMap<EmoteVariable, String> variables = new HashMap<>();
                final JSONObject jsonVariables = json.getJSONObject("variables");

                for (final String key : jsonVariables.keySet()) {
                    final EmoteVariable emoteKey = EmoteVariable.valueOf(key.toUpperCase());
                    final String value = jsonVariables.getString(key);
                    variables.put(emoteKey, value);
                }

                emoteMeta.setVariableMap(variables);
                emoteMetaList.add(emoteMeta);
            }
        }

        cachedEmoteMetas = emoteMetaList;
    }

    public static void saveDefaultEmotes(final File dir) throws IOException {
        for (final String fileName : defaultEmoteJSON.keySet()) {
            final File file = new File(dir + "/" + fileName + ".json");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                Files.copy(defaultEmoteJSON.get(fileName).openStream(), file.toPath());
            }
        }
    }

    public static void saveOtherDefaults(final File dir) throws IOException {
        for (final String fileName : otherDefaults.keySet()) {
            final File file = new File(dir + "/" + ((Object[]) otherDefaults.get(fileName))[1] + "/" + fileName);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                Files.copy(((URL) otherDefaults.get(fileName)[0]).openStream(), file.toPath());
            }
        }
    }

    public static void registerDefaultEmote(final String fileName) throws IOException {
        final ClassLoader classLoader = EmotesTF.class.getClassLoader();
        final URL url = classLoader.getResource("emotes/" + fileName + ".json");
        defaultEmoteJSON.put(fileName, url);
    }

    public static void registerOtherDefault(final String path, final String fileName, final String outputPath) throws IOException {
        final ClassLoader classLoader = EmotesTF.class.getClassLoader();
        final URL url = classLoader.getResource(path + "/" + fileName);
        otherDefaults.put(fileName, new Object[]{url, outputPath});
    }
}
