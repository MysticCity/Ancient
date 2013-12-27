package com.ancientshores.AncientRPG.Guild.Commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;

public class GuildCommandMemberlist
{
	public static void processMemberList(CommandSender sender)
	{
		Player mPlayer = (Player) sender;
		AncientRPGGuild g = AncientRPGGuild
				.getPlayersGuild(mPlayer.getName());
		if (g != null)
		{
			Set<String> names = g.gMember.keySet();
			mPlayer.sendMessage(ChatColor.GREEN + g.gName + ":");
			for (String s : names)
			{
				mPlayer.sendMessage(AncientRPGGuildRanks
						.getChatColorByRank(g.gMember.get(s)) + s);
			}
		} else
		{
			mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
					+ "You aren't in a guild.");
		}
	}
}
