package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;

public class GuildCommandRemoveTag {
	public static void processRemoveTag(CommandSender sender, String[] args) {
		if (!AncientGuild.tagenabled) {
			return;
		}
		Player mPlayer = (Player) sender;
		AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
		if (mGuild != null) {
			if (AncientGuildRanks.hasMotdRights(mGuild.gMember.get(mPlayer.getUniqueId()))) {
				mGuild.setTag("");
				AncientGuild.writeGuilds();
				mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Guild tag successfully removed!");
			}
		} else {
			mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You are in no guild.");
		}
	}
}
