package com.ancientshores.AncientRPG.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ancientshores.AncientRPG.AncientRPG;

public class AncientRPGCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (args.length) {
		case 0:
			HelpCommand.processHelp(sender, 1);
			return true;
		case 1:
			if (args[0].equalsIgnoreCase("help")) {
				HelpCommand.processHelp(sender, 1);
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission(AncientRPG.AdminPermission)) {
					ReloadCommand.reload();
					sender.sendMessage("AncientRPG reloaded!");
					return true;
				}
				// keine Permissions
				break;
			}
		case 2:
			if (args[0].equalsIgnoreCase("help")) {
				int page;
				try {
					page = Integer.parseInt(args[1]);
				}
				catch (NumberFormatException ex) {
					// keine Zahl
					return false;
				}
				
				HelpCommand.processHelp(sender, page);
				return true;
			}
		}
		return false;
	}

}
