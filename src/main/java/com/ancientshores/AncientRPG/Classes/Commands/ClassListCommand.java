package com.ancientshores.AncientRPG.Classes.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Util.PageBuilder;

public class ClassListCommand
{

	public static void showHelp(CommandSender p, String args[])
	{
		int page = 1;
		if (args.length == 2)
		{
			try
			{
				page = Integer.parseInt(args[1]);
			} catch (Exception ignored)
			{

			}
		}
		PageBuilder pb = new PageBuilder();
		for (String s : AncientRPGClass.classList.keySet())
		{
			AncientRPGClass mClass = AncientRPGClass.classList.get(s);
			if (p instanceof Player && ClassSetCommand.canSetClass(AncientRPGClass.classList.get(s), (Player) p))
			{
				if (!mClass.hidden)
				{
					String message = ChatColor.GREEN + s;
					if (mClass.description != null && mClass.description.length() > 0)
					{
						message += " - " + mClass.description;
					}
					pb.addMessage(message);
				}
			} else if (AncientRPGClass.showAllClasses)
			{
				if (!mClass.hidden)
				{
					String message = ChatColor.RED + s;
					if (mClass.description != null && mClass.description.length() > 0)
					{
						message += " - " + mClass.description;
					}
					pb.addMessage(message);
				}
			}
		}
		pb.printPage(p, page, 7);
	}
}
