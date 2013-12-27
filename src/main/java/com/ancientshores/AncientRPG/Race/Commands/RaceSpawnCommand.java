package com.ancientshores.AncientRPG.Race.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import org.bukkit.entity.Player;

public class RaceSpawnCommand {
    public static void raceSpawnCommand(Player p) {
        if (!AncientRPG.hasPermissions(p, AncientRPGRace.teleportToSpawnPermission)) {
            p.sendMessage(AncientRPG.brand2 + "You don't have permission to teleport to the spawn of your race");
        } else {
            String racename = PlayerData.getPlayerData(p.getName()).getRacename();
            for (AncientRPGRace r : AncientRPGRace.races) {
                if (r.name.equalsIgnoreCase(racename)) {
                    if (r.spawnLoc == null) {
                        return;
                    }
                    p.teleport(r.spawnLoc.toLocation());
                    p.sendMessage(AncientRPG.brand2 + "Successfully teleported to the race spawn");
                    return;
                }
            }
        }
    }
}
