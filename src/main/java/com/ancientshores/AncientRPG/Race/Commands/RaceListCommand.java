package com.ancientshores.AncientRPG.Race.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Race.AncientRPGRace;
import com.ancientshores.AncientRPG.Util.PageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RaceListCommand {
    public static void showRaces(Player p, String[] args) {
        int page = 1;
        if (args.length == 2) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (Exception ignored) {

            }
        }
        PageBuilder pb = new PageBuilder();
        if (!AncientRPG.hasPermissions(p, AncientRPGRace.listRacePermission)) {
            p.sendMessage(AncientRPG.brand2 + "You don't have permission to list the races");
            return;
        }
        for (AncientRPGRace race : AncientRPGRace.races) {
            String message = ChatColor.GREEN + race.name;
            if (race.description != null && race.description.length() > 0) {
                message += " - " + race.description;
            }
            pb.addMessage(message);
        }
        pb.printPage(p, page, 8);
    }
}
