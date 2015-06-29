package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;

public class GuildCommandMotd {
    public static void processMotd(CommandSender sender) {
        Player p = (Player) sender;
        AncientGuild guild = AncientGuild.getPlayersGuild(p.getUniqueId());
        if (guild != null)
        	if (guild.motd != null && !guild.motd.equals(""))
        		p.sendMessage(ChatColor.GREEN + "<Guild> motd: " + guild.motd);
        	else
        		p.sendMessage(ChatColor.RED + "Your guild has no motd.");
        else
            p.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
    }
}