package com.ancientshores.AncientRPG.Party.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandGrant {
    @SuppressWarnings("deprecation")
	public static void processGrant(CommandSender sender, String[] args, AncientRPG main) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer.getUniqueId());
            if (mParty != null) {
                if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (Bukkit.getPlayer(args[1]).hasPermission(AncientRPGParty.pNodeCreate)) {
                            mParty.setLeader(Bukkit.getPlayer(args[1]).getUniqueId());
                            mParty.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + Bukkit.getPlayer(mParty.getLeader()).getName() + ChatColor.BLUE + " is the new leader of the party.");
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "Succesfully granted " + ChatColor.GOLD + Bukkit.getPlayer(mParty.getLeader()).getName() + ChatColor.BLUE + " leader rights.");
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "The player you chose doesn't have the permissions to lead a party.");
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "The player you invited doesn't exist.");
                    }
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't the leader of this party.");
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't in a party.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "Correct usage: /pgrant <name>");
        }
    }
}