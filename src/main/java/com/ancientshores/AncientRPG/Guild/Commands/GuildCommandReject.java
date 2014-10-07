package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;

public class GuildCommandReject {
    public static void processReject(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientRPGGuild.invites.containsKey(mPlayer.getUniqueId())) {
            AncientRPGGuild.invites.remove(mPlayer.getUniqueId());
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "Succesfully rejected the guild invitation.");
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You weren't invited to a guild.");
        }
    }
}