package com.ancientshores.Ancient.Classes;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HelpList;
import java.io.File;
import org.bukkit.command.CommandSender;

public class AncientClassHelp
{
  static HelpList helpList;
  
  public static void printHelp(CommandSender mPlayer, String[] args)
  {
    if (helpList == null) {
      helpList = new HelpList(Ancient.plugin.getDataFolder().getPath() + File.separator + "Class" + File.separator + "classhelp.txt", "classhelp.txt");
    }
    int site = 1;
    if ((args != null) && (args.length > 1)) {
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
    if (helpList == null) {
      helpList = new HelpList(Ancient.plugin.getDataFolder().getPath() + File.separator + "Class" + File.separator + "classhelp.txt", "classhelp.txt");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\AncientClassHelp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */