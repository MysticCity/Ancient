package com.ancientshores.AncientRPG.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandReject {
    public static void processReject(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientRPGParty.invites.containsKey(mPlayer.getUniqueId())) {
            AncientRPGParty.invites.get(mPlayer.getUniqueId()).sendMessage(AncientRPG.brand2 + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + " rejected your invite.");
            AncientRPGParty.invites.remove(mPlayer);
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You weren't invited in a party.");
        }
    }
}