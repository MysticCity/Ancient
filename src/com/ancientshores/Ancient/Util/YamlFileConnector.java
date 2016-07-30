package com.ancientshores.Ancient.Util;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import com.ancientshores.Ancient.Ancient;

public class YamlFileConnector implements FileConnector {
    final Ancient instance;

    public YamlFileConnector(Ancient instance) {
        this.instance = instance;
    }

    @Override
    public String getElementOfFile(String spellName, String rowName) {
        FileConfiguration c = Ancient.plugin.getConfig();
        return c.getString(spellName + "." + rowName);
    }

    @Override
    public double getDoubleOfFile(String spellName, String rowName, File f) {
        return Double.parseDouble(getElementOfFile(spellName, rowName));
    }

    @Override
    public int getIntOfFile(String spellName, String rowName, File f) {
        return Integer.parseInt(getElementOfFile(spellName, rowName));
    }

    @Override
    public boolean getBooleanOfFile(String spellName, String rowName, File f) {
        return Boolean.parseBoolean(getElementOfFile(spellName, rowName));
    }
}