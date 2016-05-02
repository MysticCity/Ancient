package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandDisband {
    public static void processDisband(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
        if (mParty != null) {
            if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0) {
                mParty.removeAll();
                AncientParty.partys.remove(mParty);
            } else {
                mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You aren't the leader of this party.");
            }
        } else {
            mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.BLUE + "You aren't in a party.");
        }
    }
}