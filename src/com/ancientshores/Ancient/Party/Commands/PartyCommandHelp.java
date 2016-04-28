package com.ancientshores.Ancient.Party.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HelpList;
import java.io.File;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandHelp
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
      File PartyFolder = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "Party".replace('/', File.separatorChar));
      if (!PartyFolder.exists()) {
        PartyFolder.mkdir();
      }
      helpList = new HelpList(Ancient.plugin.getDataFolder().getPath() + File.separator + "Party/partyhelp.txt".replace('/', File.separatorChar), "partyhelp.txt");
    }
  }
}
