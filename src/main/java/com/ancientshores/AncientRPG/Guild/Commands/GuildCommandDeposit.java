package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;

public class GuildCommandDeposit
{
	public static void processDeposit(CommandSender sender, String[] args)
	{
		Player mPlayer = (Player) sender;
		if (args.length == 2)
		{
			AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer
					.getName());
			if (mGuild != null)
			{
				Double value;
				try
				{
					value = Double.parseDouble(args[1]);
				} catch (Exception e)
				{
					mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
							+ "String given, Decimal expected");
					return;
				}
				if (value > 0)
				{
					if (AncientRPG.economy.has(mPlayer.getName(), value))
					{
						AncientRPG.economy.withdrawPlayer(mPlayer.getName(),
								value);
						AncientRPG.economy.bankDeposit(mGuild.accountName,
								value);
						mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
								+ "Successfully deposit " + value
								+ " to the bank of your guild.");
					} else
					{
						mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
								+ "You don't have enough money.");
					}
				} else
				{
					mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
							+ "You must enter a value bigger than 0.");
				}
			} else
			{
				mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
			}
		} else
		{
			mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
					+ "Correct usage: /guild deposit <value>");
		}
	}
}
