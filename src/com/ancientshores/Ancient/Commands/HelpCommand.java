package com.ancientshores.Ancient.Commands;

import java.io.File;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HelpList;

public class HelpCommand {
	public static HelpList helpList;

	public static void processHelp(CommandSender sender, int page) {
		initHelp();
		
		if (!(sender instanceof Player)) {
			return;
		}
		Player mPlayer = (Player) sender;
		
		helpList.printToPlayer(mPlayer, page);

	}

	public static void initHelp() {
		if (helpList == null) {
			helpList = new HelpList(Ancient.plugin.getDataFolder().getPath() + File.separator + "pluginhelp.txt", "pluginhelp.txt");
		}
	}
}