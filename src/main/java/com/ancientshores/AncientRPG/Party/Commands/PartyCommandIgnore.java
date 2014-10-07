package com.ancientshores.AncientRPG.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandIgnore {
    public static void processIgnore(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length == 0) {
            if (!AncientRPGParty.mIgnoreList.contains(mPlayer.getUniqueId())) {
                AncientRPGParty.mIgnoreList.add(mPlayer.getUniqueId());
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You are ignoring all invitations now.");
            } else {
                AncientRPGParty.mIgnoreList.remove(mPlayer.getUniqueId());
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't ignoring invitations anymore.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "Correct usage: /pignore");
        }
    }
}