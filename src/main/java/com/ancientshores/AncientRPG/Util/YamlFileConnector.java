package com.ancientshores.AncientRPG.Util;

import com.ancientshores.AncientRPG.AncientRPG;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class YamlFileConnector implements FileConnector {
    final AncientRPG instance;

    public YamlFileConnector(AncientRPG instance) {
        this.instance = instance;

    }

    @Override
    public String getElementOfFile(String spellName, String rowName) {
        FileConfiguration c = AncientRPG.plugin.getConfig();
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
    public boolean getBooleanOfFile(String spellName, String rowName,
                                    File f) {
        return Boolean.parseBoolean(getElementOfFile(spellName, rowName));
    }

}
