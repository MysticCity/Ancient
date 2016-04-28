package com.ancientshores.Ancient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpList
{
  final LinkedList<String> messageList = new LinkedList();
  File file;
  
  public HelpList(String path, String ressourcePath)
  {
    createFile(path, ressourcePath);
    try
    {
      BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
      String line = bf.readLine();
      while ((line != null) && (!line.equals("")))
      {
        this.messageList.addLast(line);
        line = bf.readLine();
      }
      bf.close();
    }
    catch (FileNotFoundException e)
    {
      Ancient.plugin.getLogger().log(Level.SEVERE, "Help file " + path + " not found");
    }
    catch (IOException e)
    {
      Ancient.plugin.getLogger().log(Level.SEVERE, "Unable to read help file " + path);
    }
  }
  
  public void createFile(String path, String ressourcePath)
  {
    File f = new File(path);
    if (!f.exists()) {
      try
      {
        BufferedReader br = new BufferedReader(new InputStreamReader(Ancient.plugin.getResource(ressourcePath), "UTF-8"));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
        String s = br.readLine();
        String ges = "";
        while (s != null)
        {
          ges = ges + s + "\n";
          s = br.readLine();
        }
        out.write(ges);
        br.close();
        out.close();
      }
      catch (IOException e)
      {
        Ancient.plugin.getLogger().log(Level.SEVERE, "Failed to write help file " + f.getName());
      }
    }
    this.file = f;
  }
  
  public static String replaceChatColor(String message)
  {
    message = message.replace("{GREEN}", ChatColor.GREEN.toString());
    message = message.replace("{BLACK}", ChatColor.BLACK.toString());
    message = message.replace("{DARK_BLUE}", ChatColor.DARK_BLUE.toString());
    message = message.replace("{DARK_GREEN}", ChatColor.DARK_GREEN.toString());
    message = message.replace("{DARK_AQUA}", ChatColor.DARK_AQUA.toString());
    message = message.replace("{DARK_RED}", ChatColor.DARK_RED.toString());
    message = message.replace("{DARK_PURPLE}", ChatColor.DARK_PURPLE.toString());
    message = message.replace("{GOLD}", ChatColor.GOLD.toString());
    message = message.replace("{GRAY}", ChatColor.GRAY.toString());
    message = message.replace("{GREY}", ChatColor.GRAY.toString());
    message = message.replace("{DARK_GRAY}", ChatColor.DARK_GRAY.toString());
    message = message.replace("{DARK_GREY}", ChatColor.DARK_GRAY.toString());
    message = message.replace("{BLUE}", ChatColor.BLUE.toString());
    message = message.replace("{AQUA}", ChatColor.AQUA.toString());
    message = message.replace("{RED}", ChatColor.RED.toString());
    message = message.replace("{LIGHT_PURPLE}", ChatColor.LIGHT_PURPLE.toString());
    message = message.replace("{YELLOW}", ChatColor.YELLOW.toString());
    message = message.replace("{WHITE}", ChatColor.WHITE.toString());
    message = message.replace("{MAGIC}", ChatColor.MAGIC.toString());
    return message;
  }
  
  public void printToPlayer(CommandSender commandSender, int page)
  {
    int messagesperpage = 6;
    page--;
    int pagecount = this.messageList.size() / messagesperpage + 1;
    if ((page * messagesperpage >= this.messageList.size()) || (page <= -1))
    {
      commandSender.sendMessage(ChatColor.RED + "This page does not exist");
      return;
    }
    commandSender.sendMessage(ChatColor.DARK_BLUE + "Displaying page " + (page + 1) + " of " + pagecount);
    commandSender.sendMessage(ChatColor.DARK_BLUE + "--------------------------------------");
    for (int i = page * messagesperpage; i < page * messagesperpage + messagesperpage; i++)
    {
      if (i >= this.messageList.size()) {
        break;
      }
      commandSender.sendMessage(replaceChatColor((String)this.messageList.get(i)));
    }
    commandSender.sendMessage(ChatColor.DARK_BLUE + "--------------------------------------");
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\HelpList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */