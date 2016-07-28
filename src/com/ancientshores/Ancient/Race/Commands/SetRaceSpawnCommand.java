package com.ancientshores.Ancient.Race.Commands;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Race.AncientRace;
import com.ancientshores.Ancient.Util.SerializableLocation;

public class SetRaceSpawnCommand {
    public static void setRaceSpawn(Player p, String racename) {
        if (!p.hasPermission(AncientRace.adminRacePermission)) {
            p.sendMessage(Ancient.ChatBrand + "You don't have permission to change the spawn of this race");
        } else {
            for (AncientRace r : AncientRace.races) {
                if (r.name.equalsIgnoreCase(racename)) {
                    p.sendMessage(Ancient.ChatBrand + "Successfully set the racespawn to your current location");
                    r.spawnLoc = new SerializableLocation(p.getLocation());
                    r.writeConfig();
                    return;
                }
            }
        }
    }
}