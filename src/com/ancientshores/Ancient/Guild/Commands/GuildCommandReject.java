package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;

public class GuildCommandReject {
    public static void processReject(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientGuild.invites.containsKey(mPlayer.getUniqueId())) {
            AncientGuild.invites.remove(mPlayer.getUniqueId());
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Succesfully rejected the guild invitation.");
        } else {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You weren't invited to a guild.");
        }
    }
}