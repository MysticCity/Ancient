package com.ancientshores.AncientRPG.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandKick {
    @SuppressWarnings("deprecation")
	public static void processKick(CommandSender sender, String[] args, AncientRPG main) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer.getUniqueId());
            if (mParty != null) {
                if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0) {
                    Player mKickedPlayer = main.getServer().getPlayer(args[1]);
                    mParty.removePlayer(mKickedPlayer.getUniqueId());
                    mParty.sendMessage(AncientRPG.brand2 + ChatColor.GOLD + mKickedPlayer.getName() + ChatColor.BLUE + " was kicked out of your party.");
                    mKickedPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You were kicked out of the party.");
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't the leader of the party.");
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't in a party.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "Correct usage: /pkick <name>");
        }
    }
}