package me.mafrans.emotestf.commands;

import me.mafrans.emotestf.util.EmoteVariable;

import java.util.HashMap;
import java.util.List;

public final class EmoteMeta {
    private String name;
    private String command;
    private String description;
    private String usage;
    private String author;
    private HashMap<List<EmoteVariable>, String[]> lineMap;
    private HashMap<EmoteVariable, String> variableMap;

    public EmoteMeta() {
    }

    public void setUsage(final String usage) {
        this.usage = usage;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public void setLineMap(final HashMap<List<EmoteVariable>, String[]> lineMap) {
        this.lineMap = lineMap;
    }

    public void setVariableMap(final HashMap<EmoteVariable, String> variableMap) {
        this.variableMap = variableMap;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUsage() {
        return this.usage;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getCommand() {
        return this.command;
    }

    public HashMap<EmoteVariable, String> getVariableMap() {
        return this.variableMap;
    }

    public HashMap<List<EmoteVariable>, String[]> getLineMap() {
        return this.lineMap;
    }
}
