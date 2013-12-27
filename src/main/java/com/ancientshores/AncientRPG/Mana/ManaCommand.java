package com.ancientshores.AncientRPG.Mana;

import com.ancientshores.AncientRPG.AncientRPG;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.PlayerData;

public class ManaCommand
{
	public static void processManaCommand(CommandSender sender)
	{
		if(sender instanceof Player)
		{
			PlayerData pd = PlayerData.getPlayerData(sender.getName());
			sender.sendMessage(AncientRPG.brand2 + "Your mana regeneration per " +  pd.getManasystem().manareginterval + " seconds is " + pd.getManasystem().manareg);
			sender.sendMessage(AncientRPG.brand2 + "You currently have " + pd.getManasystem().curmana + " mana, your max mana is " + pd.getManasystem().maxmana);
		}
	}
}
