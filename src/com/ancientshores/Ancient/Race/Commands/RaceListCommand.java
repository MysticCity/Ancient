package com.ancientshores.Ancient.Race.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Race.AncientRace;
import com.ancientshores.Ancient.Util.PageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RaceListCommand
{
  public static void showRaces(Player p, String[] args)
  {
    int page = 1;
    if (args.length == 2) {
      try
      {
        page = Integer.parseInt(args[1]);
      }
      catch (Exception ignored) {}
    }
    PageBuilder pb = new PageBuilder();
    if (!p.hasPermission("Ancient.Race.list"))
    {
      p.sendMessage(Ancient.brand2 + "You don't have permission to list the races");
      return;
    }
    for (AncientRace race : AncientRace.races)
    {
      String message = ChatColor.GREEN + race.name;
      if ((race.description != null) && (race.description.length() > 0)) {
        message = message + " - " + race.description;
      }
      pb.addMessage(message);
    }
    pb.printPage(p, page, 8);
  }
}
