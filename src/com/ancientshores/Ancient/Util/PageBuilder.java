package com.ancientshores.Ancient.Util;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PageBuilder
{
  final ArrayList<String> messages = new ArrayList();
  
  public void addMessage(String s)
  {
    this.messages.add(s);
  }
  
  public void printPage(CommandSender p, int page, int messagesperpage)
  {
    page--;
    int pagecount = this.messages.size() / messagesperpage + 1;
    if ((page * messagesperpage >= this.messages.size()) || (page <= -1))
    {
      p.sendMessage(ChatColor.RED + "This page does not exist");
      return;
    }
    p.sendMessage(ChatColor.DARK_BLUE + "Displaying page " + (page + 1) + " of " + pagecount);
    p.sendMessage(ChatColor.DARK_BLUE + "--------------------------------------");
    for (int i = page * messagesperpage; i < page * messagesperpage + messagesperpage; i++)
    {
      if (i >= this.messages.size()) {
        break;
      }
      p.sendMessage((String)this.messages.get(i));
    }
    p.sendMessage(ChatColor.DARK_BLUE + "--------------------------------------");
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Util\PageBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */