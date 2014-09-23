package com.ancientshores.AncientRPG.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandLeave {
    public static void processLeave(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientRPGParty.getPlayersParty(mPlayer.getUniqueId()) != null) {
            AncientRPGParty mPlayersParty = AncientRPGParty.getPlayersParty(mPlayer.getUniqueId());
            mPlayersParty.removePlayer(mPlayer.getUniqueId());
            mPlayersParty.giveNextLeader();
            mPlayersParty.sendMessage(AncientRPG.brand2 + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + " left your party.");
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "Successfully left the party.");
            if (mPlayersParty.getMemberNumber() == 0) {
                AncientRPGParty.partys.remove(mPlayersParty);
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't in a party.");
        }
    }
}