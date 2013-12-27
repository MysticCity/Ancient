package com.ancientshores.AncientRPG.Race.Commands;

import java.io.File;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.HelpList;

public class RaceHelpCommand
{
	public static HelpList helpList;

	public static void processHelp(CommandSender sender, String[] args)
	{
		if (helpList == null)
			initHelp();
		Player mPlayer = (Player) sender;
		int site = 1;
		if (args.length == 2)
		{
			try
			{
				site = Integer.parseInt(args[1]);
			} catch (Exception ignored)
			{

			}
		}
		helpList.printToPlayer(mPlayer, site);

	}

	public static void initHelp()
	{
		if (helpList == null)
		{
			if (!new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Races").exists())
			{
				new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Races").mkdirs();
			}
			helpList = new HelpList(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "Races" + File.separator + "racehelp.txt", "racehelp.txt");
		}
	}
}
