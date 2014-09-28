package com.ancientshores.AncientRPG.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandAccept {
    public static void processAccept(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientRPGParty.invites.containsKey(mPlayer.getUniqueId())) {
            if (AncientRPGParty.invites.get(mPlayer.getUniqueId()).getMemberNumber() < AncientRPGParty.maxPlayers) {
                AncientRPGParty inviteParty = AncientRPGParty.invites.get(mPlayer.getUniqueId());
                if (inviteParty != null && inviteParty.getMemberNumber() > 0) {
                    inviteParty.sendMessage(AncientRPG.brand2 + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + " joined your party.");
                    AncientRPGParty.invites.get(mPlayer.getUniqueId()).addPlayer(mPlayer.getUniqueId());
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You joined a party.");
                } else {
                    AncientRPGParty.invites.remove(mPlayer.getUniqueId());
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "The party you tried to join doesn't exist anymore.");
                }
            } else {
                AncientRPGParty.invites.remove(mPlayer.getUniqueId());
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "This party is already full.");
            }
            AncientRPGParty.invites.remove(mPlayer.getUniqueId());
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You weren't invited in a party.");
        }
    }
}