package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Util.FlatFileConnector;
import java.io.File;
import java.util.UUID;

public class Variables
{
  static final FlatFileConnector ffc = new FlatFileConnector(Ancient.plugin);
  
  public static int getParameterIntByString(UUID uuid, String name, File f)
  {
    PlayerData pd = PlayerData.getPlayerData(uuid);
    int level = 1;
    if (pd.getXpSystem() != null) {
      level = pd.getXpSystem().level;
    }
    return ffc.getIntOfFile("" + level, name, f);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Variables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */