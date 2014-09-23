package com.ancientshores.AncientRPG.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandDisband {
    public static void processDisband(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer.getUniqueId());
        if (mParty != null) {
            if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0) {
                mParty.removeAll();
                AncientRPGParty.partys.remove(mParty);
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't the leader of this party.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't in a party.");
        }
    }
}