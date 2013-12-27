package com.ancientshores.AncientRPG.Util;

import java.io.File;

public interface FileConnector {
    public String getElementOfFile(String spellName, String rowName);

    public double getDoubleOfFile(String spellname, String rowName, File path);

    public int getIntOfFile(String spellname, String rowName, File path);

    public boolean getBooleanOfFile(String spellname, String rowName, File path);
}
