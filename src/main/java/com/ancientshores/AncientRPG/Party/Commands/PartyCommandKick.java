package com.ancientshores.AncientRPG.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandKick
{
	public static void processKick(CommandSender sender, String[] args,
			AncientRPG main){
		Player mPlayer = (Player) sender;
		if (args.length == 2)
		{
			AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
			if (mParty != null)
			{
				if (mPlayer == mParty.mLeader)
				{
					Player mKickedPlayer = main.getServer().getPlayer(
							args[1]);
					mParty.removePlayer(mKickedPlayer);
					mParty.sendMessage(AncientRPG.brand2 + ChatColor.GOLD
							+ mKickedPlayer.getName() + ChatColor.BLUE
							+ " was kicked out of your party.");
					mKickedPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
							+ "You were kicked out of the party.");
				} else
				{
					mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
							+ "You aren't the leader of the party.");
				}
			} else
			{
				mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
						+ "You aren't in a party.");
			}
		} else
		{
			mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
					+ "Correct usage: /pkick <name>");
		}
	}
}
