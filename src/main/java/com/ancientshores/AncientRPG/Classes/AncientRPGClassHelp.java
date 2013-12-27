package com.ancientshores.AncientRPG.Classes;

import java.io.File;

import org.bukkit.command.CommandSender;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.HelpList;

public class AncientRPGClassHelp
{
	static HelpList helpList;

	public static void printHelp(CommandSender mPlayer, String[] args)
	{
		if(helpList == null)
			helpList = new HelpList(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Class" + File.separator + "classhelp.txt", "classhelp.txt");
		int site = 1;
		if(args != null && args.length > 1)
		{
			try
			{
				site = Integer.parseInt(args[1]);
			}
			catch(Exception ignored)
			{
				
			}
		}
		helpList.printToPlayer(mPlayer, site);
	}
	public static void initHelp()
	{if(helpList == null)
		helpList = new HelpList(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Class" + File.separator + "classhelp.txt", "classhelp.txt");
	}
}
