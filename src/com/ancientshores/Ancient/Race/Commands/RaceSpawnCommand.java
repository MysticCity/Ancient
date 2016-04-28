package com.ancientshores.Ancient.Race.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Race.AncientRace;

public class RaceSpawnCommand {
    public static void raceSpawnCommand(Player p) {
        if (!p.hasPermission(AncientRace.teleportToSpawnPermission)) {
            p.sendMessage(Ancient.brand2 + "You don't have permission to teleport to the spawn of your race");
        } else {
            String racename = PlayerData.getPlayerData(p.getUniqueId()).getRacename();
            for (AncientRace r : AncientRace.races) {
                if (r.name.equalsIgnoreCase(racename)) {
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