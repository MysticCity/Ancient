package com.ancientshores.AncientRPG.Party.Commands;

import java.io.File;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.HelpList;

public class PartyCommandHelp
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
			File PartyFolder = new File(AncientRPG.plugin.getDataFolder()
					.getPath()
					+ File.separator
					+ "Party".replace('/',
							File.separatorChar));
			if (!PartyFolder.exists())
				PartyFolder.mkdir();
			helpList = new HelpList(AncientRPG.plugin.getDataFolder().getPath()
					+ File.separator
					+ "Party/partyhelp.txt".replace('/',
							File.separatorChar), "partyhelp.txt");
		}
	}
}
