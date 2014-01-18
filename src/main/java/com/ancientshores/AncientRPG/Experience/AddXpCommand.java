package com.ancientshores.AncientRPG.Experience;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class AddXpCommand {
    public static void addXp(CommandSender cs, String[] args) {
        String playername = "";
        if (cs instanceof Player && !AncientRPG.hasPermissions((Player) cs, AncientRPGExperience.nodeXPAdmin)) {
            cs.sendMessage(ChatColor.GOLD + "[" + AncientRPG.brand + "] " + ChatColor.YELLOW + "You don't have the permission to use this command");
            return;
        }
        if (args.length < 2) {
            cs.sendMessage(ChatColor.GOLD + "[" + AncientRPG.brand + "] " + ChatColor.YELLOW + "Correct usage is level setxp <amount> [player]");
            return;
        }
        int amount;
        if (args.length == 2) {
            if (!(cs instanceof Player)) {
                cs.sendMessage(ChatColor.GOLD + "[" + AncientRPG.brand + "] " + ChatColor.YELLOW + "Correct usage is level setxp amount player");
                return;
            }
            playername = cs.getName();
        }
        if (args.length == 3) {
            playername = args[2];
        }
        try {
            amount = Integer.parseInt(args[1]);
        } catch (Exception e) {
            cs.sendMessage(ChatColor.GOLD + "[" + AncientRPG.brand + "] " + ChatColor.YELLOW + "Expected Integer, recieved string");
            return;
        }
        PlayerData pd = PlayerData.playerHasPlayerData(playername);
        if (pd == null) {
            cs.sendMessage(ChatColor.GOLD + "[" + AncientRPG.brand + "] " + ChatColor.YELLOW + "Player not found");
        }
        if (pd != null) {
            pd.getXpSystem().xp += amount;
            pd.getXpSystem().addXP(0, false);
            cs.sendMessage(ChatColor.GOLD + "[" + AncientRPG.brand + "] " + ChatColor.YELLOW + "Successfully added " + amount + " to the experience of the player " + playername);
        }
    }
}