package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;

public class GuildCommandRemoveMotd {
	public static void processRemoveMOTD(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		AncientGuild guild = AncientGuild.getPlayersGuild(player.getUniqueId());
		if (guild != null) {
			if (AncientGuildRanks.hasMotdRights(guild.gMember.get(player.getUniqueId()))) {
				guild.motd = "";
				player.sendMessage(Ancient.brand2 + ChatColor.GREEN + "MOTD was successfully removed!");
				AncientGuild.writeGuilds();
			} else {
				player.sendMessage(Ancient.brand2 + ChatColor.RED + "You do not have the rights to change the MOTD.");
			}
		} else {
			player.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
		}
	}
}