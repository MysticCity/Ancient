package com.ancientshores.AncientRPG.Party.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandList {
    public static void processList(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientRPGParty.getPlayersParty(mPlayer.getUniqueId()) != null) {
            if (mPlayer.hasPermission("")) { // ??? --- mega sinnlos
                AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer.getUniqueId());
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "In your party are:");
                for (UUID uuid : mParty.getMembers()) {
                    if (uuid != null) {
                        if (uuid == AncientRPGParty.getPlayersParty(mPlayer.getUniqueId()).getLeader()) {
                            mPlayer.sendMessage(ChatColor.GREEN + Bukkit.getPlayer(uuid).getName() + "(Leader)");
                        } else {
                            mPlayer.sendMessage(ChatColor.GOLD + Bukkit.getPlayer(uuid).getName());
                        }
                    }
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You don't have the permissions to list the party members.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't in a party.");
        }
    }
}