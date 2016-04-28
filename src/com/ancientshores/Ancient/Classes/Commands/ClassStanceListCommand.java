package com.ancientshores.Ancient.Classes.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Util.PageBuilder;

public class ClassStanceListCommand {
    public static void showStances(Player p, String[] args) {
        int page = 1;
        if (args.length == 2) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (Exception ignored) {

            }
        }
        PageBuilder pb = new PageBuilder();
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        AncientClass c = AncientClass.classList.get(pd.getClassName().toLowerCase());
        if (c != null) {
            pb.addMessage(Ancient.brand2 + "Class-stances:");
            pb.addMessage(ChatColor.STRIKETHROUGH + "--------------------------------");
            for (String s : c.stances.keySet()) {
                if (ClassSetStanceCommand.canSetStanceClass(c.stances.get(s), p)) {
                    pb.addMessage(ChatColor.GREEN + s);
                } else {
                    pb.addMessage(ChatColor.RED + s);
                }
            }
            pb.addMessage(ChatColor.STRIKETHROUGH + "--------------------------------");
            pb.printPage(p, page, 7);
        }
    }
}