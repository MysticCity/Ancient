package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandLeave {
    public static void processLeave(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientParty.getPlayersParty(mPlayer.getUniqueId()) != null) {
            AncientParty mPlayersParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
            mPlayersParty.removePlayer(mPlayer.getUniqueId());
            mPlayersParty.giveNextLeader();
            mPlayersParty.sendMessage(Ancient.brand2 + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + " left your party.");
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "Successfully left the party.");
            if (mPlayersParty.getMemberNumber() == 1) {
                mPlayersParty.sendMessage(Ancient.brand2 + ChatColor.RED + "Your party has been deleted.");
                AncientParty.partys.remove(mPlayersParty);
            }
        } else {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't in a party.");
        }
    }
}