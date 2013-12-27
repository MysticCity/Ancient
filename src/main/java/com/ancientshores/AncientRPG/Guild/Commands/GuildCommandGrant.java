package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;

public class GuildCommandGrant
{
	public static void processGrant(CommandSender sender, String[] args)
	{
		Player mPlayer = (Player) sender;
		if (args.length == 3)
		{
			AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer
					.getName());
			if (mGuild != null)
			{
				Player grantedplayer = AncientRPG.plugin.getServer().getPlayer(
						args[1]);
				if (grantedplayer != null && grantedplayer.isOnline()
						&& mGuild.gMember.containsKey(grantedplayer.getName()))
				{
					AncientRPGGuildRanks newrank = AncientRPGGuildRanks
							.getRankByString(args[2]);
					if (newrank != null)
					{
						switch (mGuild.gMember.get(mPlayer.getName()))
						{
						case LEADER:
							if (newrank == AncientRPGGuildRanks.LEADER
									&& grantedplayer != mPlayer)
							{
								mGuild.gLeader = grantedplayer.getName();
								mGuild.gMember.put(grantedplayer.getName(),
										AncientRPGGuildRanks.LEADER);
								mGuild.gMember.put(mPlayer.getName(),
										AncientRPGGuildRanks.CO_LEADER);
								mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN
										+ "<Guild>: " + ChatColor.DARK_RED
										+ grantedplayer.getName()
										+ ChatColor.GREEN
										+ " is the new Leader of the guild"
										+ ".");
								return;
							} else if (grantedplayer != mPlayer)
							{
								mGuild.gMember.put(grantedplayer.getName(),
										newrank);
								mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN
										+ "<Guild>: "
										+ AncientRPGGuildRanks
												.getChatColorByRank(newrank)
										+ grantedplayer.getName()
										+ AncientRPGGuildRanks
												.getChatColorByRank(newrank)
										+ ChatColor.GREEN + " is now a "
										+ newrank.getguildrank() + ".");
							}
							return;
						case CO_LEADER:
							if (newrank == AncientRPGGuildRanks.MEMBER
									|| newrank == AncientRPGGuildRanks.OFFICER
									|| newrank == AncientRPGGuildRanks.TRIAL)
							{
								mGuild.gMember.put(grantedplayer.getName(),
										newrank);
								mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN
										+ "<Guild>: "
										+ grantedplayer.getName()
										+ AncientRPGGuildRanks
												.getChatColorByRank(newrank)
										+ ChatColor.GREEN + " is now a "
										+ newrank.getguildrank() + ".");

							} else
							{
								mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
										+ "You haven't the rights to do that.");
							}
							return;
						case OFFICER:
							if (newrank == AncientRPGGuildRanks.MEMBER
									|| newrank == AncientRPGGuildRanks.OFFICER
									|| newrank == AncientRPGGuildRanks.TRIAL)
							{
								mGuild.gMember.put(grantedplayer.getName(),
										newrank);
								mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN
										+ "<Guild>: "
										+ grantedplayer.getName()
										+ AncientRPGGuildRanks
												.getChatColorByRank(newrank)
										+ ChatColor.GREEN + " is now a "
										+ newrank.getguildrank() + ".");
							} else
							{
								mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
										+ "You haven't the rights to do that.");
							}
							return;
						default:
							break;
						}
					} else
					{
						mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
								+ "This rank doesn't exist.");
					}
				} else
				{
					mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
							+ "This player doesn't exist, isn't in your guild or isn't online.");
				}
			} else
			{
				mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are in no guild");
			}
		} else
		{
			mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
					+ "Correct usage /guild grant <name> <rank>");
		}
	}
}
