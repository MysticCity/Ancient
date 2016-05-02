package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;

public class GuildCommandDisband {
    public static void processDisband(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
        if (mGuild != null) {
            if (mGuild.gLeader.compareTo(mPlayer.getUniqueId()) == 0) {
                mGuild.disband(false);
            } else {
                mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "Only the leader can disband the guild.");
            }
        } else {
            mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "You aren't in a guild.");
        }
    }
}