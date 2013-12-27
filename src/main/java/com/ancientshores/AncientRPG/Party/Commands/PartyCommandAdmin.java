package com.ancientshores.AncientRPG.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandAdmin
{
	public static void processAdmin(CommandSender sender, String[] args,
			AncientRPG main){
		Player mPlayer = (Player) sender;
		if (AncientRPG.hasPermissions(mPlayer, AncientRPGParty.pNodeAdmin))
		{
			if (args.length == 2)
			{
				if (args[1].equals("show"))
				{
					int i = 1;
					for (AncientRPGParty p : AncientRPGParty.partys)
					{
						mPlayer.sendMessage(ChatColor.BLUE + "" + i + ": "
								+ ChatColor.GREEN + p.mLeader.getName());
						i++;
					}
				}
			}
			if (args.length == 3)
			{
				if (args[1].equals("pl"))
				{
					Player playertolist = main.getServer().getPlayer(
							args[2]);
					if (playertolist != null)
					{
						if (AncientRPGParty.getPlayersParty(playertolist) != null)
						{
							AncientRPGParty mParty = AncientRPGParty.getPlayersParty(playertolist);
							mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
									+ "In this party are:");
							for (Player p : mParty.Member)
							{
								if (p != null)
								{
									if (p == AncientRPGParty.getPlayersParty(mPlayer).mLeader)
									{
										mPlayer.sendMessage(ChatColor.GREEN
												+ p.getName() + "(Leader)");
									} else
									{
										mPlayer.sendMessage(ChatColor.GOLD
												+ p.getName());
									}
								}
							}
						} else
						{
							mPlayer.sendMessage(ChatColor.BLUE
									+ "This user isn't in a Party");
						}
					}
				} else if (args[1].equals("db"))
				{
					Player playertolist = main.getServer().getPlayer(
							args[2]);
					if (playertolist != null)
					{
						if (AncientRPGParty.getPlayersParty(playertolist) != null)
						{
							AncientRPGParty mParty = AncientRPGParty.getPlayersParty(playertolist);
							mParty.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
									+ "Your party has been disbandend by an admin.");
							mParty.removeAll();
							AncientRPGParty.partys.remove(mParty);
						} else
						{
							mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
									+ "This user isn't in a Party");
						}
					} else
					{
						mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
								+ "This player isn't in a party.");
					}
				}
			}
		} else
		{
			mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
					+ "You aren't a party admin");
		}
	}
}
