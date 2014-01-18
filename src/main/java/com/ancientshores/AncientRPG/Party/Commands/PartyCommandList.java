package com.ancientshores.AncientRPG.Party.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandList {
    public static void processList(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientRPGParty.getPlayersParty(mPlayer) != null) {
            if (AncientRPG.hasPermissions(mPlayer, "")) {
                AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "In your party are:");
                for (Player p : mParty.getMembers()) {
                    if (p != null) {
                        if (p == AncientRPGParty.getPlayersParty(mPlayer).getLeader()) {
                            mPlayer.sendMessage(ChatColor.GREEN
                                    + p.getName() + "(Leader)");
                        } else {
                            mPlayer.sendMessage(ChatColor.GOLD
                                    + p.getName());
                        }
                    }
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                        + "You don't have the permissions to list the party members.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't in a party.");
        }
    }
}