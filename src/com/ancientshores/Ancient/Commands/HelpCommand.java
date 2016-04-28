package com.ancientshores.Ancient.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HelpList;
import java.io.File;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand
{
  public static HelpList helpList;
  
  public static void processHelp(CommandSender sender, int page)
  {
    
    if (!(sender instanceof Player)) {
      return;
    }
    Player mPlayer = (Player)sender;
    
    helpList.printToPlayer(mPlayer, page);
  }
  
  public static void initHelp()
  {
    if (helpList == null) {
      helpList = new HelpList(Ancient.plugin.getDataFolder().getPath() + File.separator + "pluginhelp.txt", "pluginhelp.txt");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Commands\HelpCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */