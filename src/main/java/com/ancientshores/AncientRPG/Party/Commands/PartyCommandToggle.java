package com.ancientshores.AncientRPG.Party.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Listeners.AncientRPGPlayerListener;

public class PartyCommandToggle
{
	public static void processToggle(CommandSender sender)
	{
		Player mPlayer = (Player) sender;
		if(!AncientRPGPlayerListener.togglepartylist.contains(mPlayer))
		{
			AncientRPGPlayerListener.togglepartylist.add(mPlayer);
			mPlayer.sendMessage(AncientRPG.brand2 + "Party chat activated");
		}
		else
		{
			AncientRPGPlayerListener.togglepartylist.remove(mPlayer);
			mPlayer.sendMessage(AncientRPG.brand2 + "Party chat deacticated");
		}
	}
}
