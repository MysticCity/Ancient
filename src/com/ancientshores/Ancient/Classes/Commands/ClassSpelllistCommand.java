package com.ancientshores.Ancient.Classes.Commands;

import com.ancientshores.Ancient.Spells.Commands.SpellsCommand;
import org.bukkit.entity.Player;

public class ClassSpelllistCommand
{
  public static void spellListCommand(String[] args, Player p)
  {
    int page = 1;
    if (args.length == 2) {
      try
      {
        page = Integer.parseInt(args[1]);
      }
      catch (Exception ignored) {}
    }
    showSpellList(p, page);
  }
  
  public static void showSpellList(Player p, int page)
  {
    SpellsCommand.showSpellList(p, page);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Commands\ClassSpelllistCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */