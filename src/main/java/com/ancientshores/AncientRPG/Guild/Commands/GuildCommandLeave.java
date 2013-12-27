package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;

public class GuildCommandLeave
{
	public static void processLeave(CommandSender sender)
	{
		Player mPlayer = (Player) sender;
		AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(mPlayer
				.getName());
		if (guild != null)
		{
			ChatColor farbe = AncientRPGGuildRanks.getChatColorByRank(guild
					.getgMember().get(mPlayer.getName()));
			guild.gMember.remove(mPlayer.getName());
			AncientRPGGuild.writeGuild(guild);
			guild.broadcastMessage(AncientRPG.brand2 + farbe + mPlayer.getName() + ChatColor.GREEN
					+ " left the guild.");
			mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
					+ "Succesfully left your guild.");
			mPlayer.setDisplayName(mPlayer.getName());
			if (mPlayer.getName().equals(guild.gLeader) && guild.gMember.size() > 0)
			{
				mPlayer.sendMessage(AncientRPG.brand2 + "You can't leave the guild as a leader unless you are the only one in the guild.");
			}
		} else
		{
			mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
		}
	}
}
