package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandAccept {
    public static void processAccept(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientParty.invites.containsKey(mPlayer.getUniqueId())) {
            if (AncientParty.invites.get(mPlayer.getUniqueId()).getMemberNumber() < AncientParty.maxPlayers) {
                AncientParty inviteParty = AncientParty.invites.get(mPlayer.getUniqueId());
                if (inviteParty != null && inviteParty.getMemberNumber() > 0) {
                    inviteParty.sendMessage(Ancient.ChatBrand + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + " joined your party.");
                    AncientParty.invites.get(mPlayer.getUniqueId()).addPlayer(mPlayer.getUniqueId());
                    mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You joined a party.");
                } else {
                    AncientParty.invites.remove(mPlayer.getUniqueId());
                    mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "The party you tried to join doesn't exist anymore.");
                }
            } else {
                AncientParty.invites.remove(mPlayer.getUniqueId());
                mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "This party is already full.");
            }
            AncientParty.invites.remove(mPlayer.getUniqueId());
        } else {
            mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You weren't invited in a party.");
        }
    }
}