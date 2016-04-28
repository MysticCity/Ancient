package com.ancientshores.Ancient.Race.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Race.AncientRace;
import com.ancientshores.Ancient.Util.SerializableLocation;
import org.bukkit.entity.Player;

public class RaceSpawnCommand
{
  public static void raceSpawnCommand(Player p)
  {
    String racename;
    if (!p.hasPermission("Ancient.Race.spawn"))
    {
      p.sendMessage(Ancient.brand2 + "You don't have permission to teleport to the spawn of your race");
    }
    else
    {
      racename = PlayerData.getPlayerData(p.getUniqueId()).getRacename();
      for (AncientRace r : AncientRace.races) {
        if (r.name.equalsIgnoreCase(racename))
        {
          if (r.spawnLoc == null) {
            return;
          }
          p.teleport(r.spawnLoc.toLocation());
          p.sendMessage(Ancient.brand2 + "Successfully teleported to the race spawn");
          return;
        }
      }
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Race\Commands\RaceSpawnCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */