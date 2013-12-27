package com.ancientshores.AncientRPG.Classes.Spells;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Util.FlatFileConnector;
import org.bukkit.entity.Player;

import java.io.File;

public class Variables {
    static final FlatFileConnector ffc = new FlatFileConnector(AncientRPG.plugin);

    public static int getParameterIntByString(Player mPlayer, String name, File f) {
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
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
