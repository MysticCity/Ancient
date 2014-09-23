package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;

public class GuildCommandList {
    public static void processList(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args[0].equalsIgnoreCase("list")) {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "List of guilds:");
            for (AncientRPGGuild g : AncientRPGGuild.guilds) {
                mPlayer.sendMessage(ChatColor.GREEN + g.guildName + " " + ChatColor.DARK_RED + g.gLeader + ChatColor.GREEN + " (" + g.gMember.size() + ")");
            }
        }
    }
}