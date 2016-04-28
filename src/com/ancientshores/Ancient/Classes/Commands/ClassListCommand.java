package com.ancientshores.Ancient.Classes.Commands;

import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;
import com.ancientshores.Ancient.Util.PageBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassListCommand
{
  public static void showHelp(CommandSender sender, String[] args)
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
    for (String s : AncientClass.classList.keySet())
    {
      AncientClass mClass = (AncientClass)AncientClass.classList.get(s);
      if (((sender instanceof Player)) && (ClassSetCommand.canSetClass((AncientClass)AncientClass.classList.get(s), (Player)sender)))
      {
        if (!mClass.hidden)
        {
          String message = ChatColor.GREEN + s;
          if ((mClass.description != null) && (mClass.description.length() > 0)) {
            message = message + " - " + mClass.description;
          }
          pb.addMessage(message);
        }
      }
      else if ((AncientClass.showAllClasses) && 
        (!mClass.hidden))
      {
        String message = ChatColor.RED + s;
        if ((mClass.description != null) && (mClass.description.length() > 0)) {
          message = message + " - " + mClass.description;
        }
        pb.addMessage(message);
      }
    }
    pb.printPage(sender, page, 7);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Commands\ClassListCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */