package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;

public class GuildCommandMotd {
    public static void processMotd(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
        if (mGuild != null) {
            mPlayer.sendMessage(ChatColor.GREEN + "<Guild> motd: " + mGuild.motd);
        } else {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
        }
    }
}