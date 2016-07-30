package com.ancientshores.Ancient.Classes.Spells;

import java.io.File;
import java.util.UUID;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Util.FlatFileConnector;

public class Variables {
    static final FlatFileConnector ffc = new FlatFileConnector(Ancient.plugin);

    public static int getParameterIntByString(UUID uuid, String name, File f) {
        PlayerData pd = PlayerData.getPlayerData(uuid);
        int level = 1;
        if (pd.getXpSystem() != null) {
            level = pd.getXpSystem().level;
        }
        return ffc.getIntOfFile("" + level, name, f);
    }
    
    /*public static String getParameterStringByString(Player mPlayer, String name, File f)
    {
		PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
		int level = 1;
		if(pd.getXpSystem() != null)
			level = pd.getXpSystem().level;
		return ffc.getElementOfFile("" + level, name, f);
	}*/
}