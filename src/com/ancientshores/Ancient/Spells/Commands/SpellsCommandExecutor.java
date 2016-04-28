package com.ancientshores.Ancient.Spells.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpellsCommandExecutor
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (label.equals("spells"))
    {
      if ((args.length >= 2) && (args[0].equalsIgnoreCase("info")))
      {
        SpellsInfoCommand.spellsInfo(args, sender);
        return true;
      }
      if ((args.length >= 1) && (args[0].equalsIgnoreCase("addspellfreezone")))
      {
        AddSpellFreeZoneCommand.onCommand(sender, args);
        return true;
      }
      if ((args.length >= 1) && (args[0].equalsIgnoreCase("bind")) && ((sender instanceof Player)))
      {
        SpellBindCommand.bindCommand(args, (Player)sender);
        return true;
      }
      if ((args.length >= 1) && (args[0].equalsIgnoreCase("bindslot")) && ((sender instanceof Player)))
      {
        SpellBindCommand.bindSlotCommand(args, (Player)sender);
        return true;
      }
      if ((args.length >= 1) && (args[0].equalsIgnoreCase("removespellfreezone")))
      {
        RemoveSpellFreeZoneCommand.onCommand(sender, args);
        return true;
      }
      if ((args.length <= 1) && ((sender instanceof Player)))
      {
        SpellsCommand.spellListCommand(args, (Player)sender);
        return true;
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Spells\Commands\SpellsCommandExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */