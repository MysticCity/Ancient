package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HelpList;
import java.io.File;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandHelp
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
      File PartyFolder = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "Guilds".replace('/', File.separatorChar));
      if (!PartyFolder.exists()) {
        PartyFolder.mkdir();
      }
      helpList = new HelpList(Ancient.plugin.getDataFolder().getPath() + File.separator + "Guilds/guildhelp.txt".replace('/', File.separatorChar), "guildhelp.txt");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandHelp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */