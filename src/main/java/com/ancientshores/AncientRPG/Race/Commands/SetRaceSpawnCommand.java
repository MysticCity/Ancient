package com.ancientshores.AncientRPG.Race.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import com.ancientshores.AncientRPG.Util.SerializableLocation;
import org.bukkit.entity.Player;

public class SetRaceSpawnCommand {
    public static void setRaceSpawn(Player p, String racename) {
        if (!AncientRPG.hasPermissions(p, AncientRPGRace.adminRacePermission)) {
            p.sendMessage(AncientRPG.brand2 + "You don't have permission to change the spawn of this race");
        } else {
            for (AncientRPGRace r : AncientRPGRace.races) {
                if (r.name.equalsIgnoreCase(racename)) {
                    p.sendMessage(AncientRPG.brand2 + "Successfully set the racespawn to your current location");
                    r.spawnLoc = new SerializableLocation(p.getLocation());
                    r.writeConfig();
                    return;
                }
            }
        }
    }
}