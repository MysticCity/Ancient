package com.ancientshores.Ancient.Race.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HelpList;
import java.io.File;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RaceHelpCommand
{
  public static HelpList helpList;
  
  public static void processHelp(CommandSender sender, String[] args)
  {
    if (helpList == null) {
      initHelp();
    }
    Player mPlayer = (Player)sender;
    int site = 1;
    if (args.length == 2) {
      try
      {
        site = Integer.parseInt(args[1]);
      }
      catch (Exception ignored) {}
    }
    helpList.printToPlayer(mPlayer, site);
  }
  
  public static void initHelp()
  {
    if (helpList == null)
    {
      if (!new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "Races").exists()) {
        new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "Races").mkdirs();
      }
      helpList = new HelpList(Ancient.plugin.getDataFolder().getPath() + File.separator + "Races" + File.separator + "racehelp.txt", "racehelp.txt");
    }
  }
}
