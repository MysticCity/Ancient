package com.ancientshores.Ancient.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AncientCommand
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    switch (args.length)
    {
    case 0: 
      HelpCommand.processHelp(sender, 1);
      return true;
    case 1: 
      if (args[0].equalsIgnoreCase("help"))
      {
        HelpCommand.processHelp(sender, 1);
        return true;
      }
      if (args[0].equalsIgnoreCase("reload"))
      {
        if (!sender.hasPermission("Ancient.admin")) {
          break label126;
        }
        ReloadCommand.reload();
        sender.sendMessage("Ancient reloaded!");
        return true;
      }
    case 2: 
      if (args[0].equalsIgnoreCase("help"))
      {
        int page;
        try
        {
          page = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException ex)
        {
          return false;
        }
        HelpCommand.processHelp(sender, page);
        return true;
      }
      break;
    }
    label126:
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Commands\AncientCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */