package com.ancientshores.AncientRPG.Classes.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Util.PageBuilder;

public class ClassStanceListCommand
{
	public static void showStances(Player p, String[] args)
	{
		int page = 1;
		if(args.length == 2)
		{
			try
			{
				page = Integer.parseInt(args[1]);
			}
			catch(Exception ignored)
			{
				
			}
		}
		PageBuilder pb = new PageBuilder();
		PlayerData pd = PlayerData.getPlayerData(p.getName());
		AncientRPGClass c = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
		if (c != null)
		{
			pb.addMessage(AncientRPG.brand2 + "Class-stances:");
			pb.addMessage(ChatColor.STRIKETHROUGH + "--------------------------------");
			for (String s : c.stances.keySet())
			{
				if(ClassSetStanceCommand.canSetStanceClass(c.stances.get(s), p))
				{
					pb.addMessage(ChatColor.GREEN + s);
				}
				else
				{
					pb.addMessage(ChatColor.RED + s);
				}
			}
			pb.addMessage(ChatColor.STRIKETHROUGH + "--------------------------------");
			pb.printPage(p, page, 7);
		}
	}
}
