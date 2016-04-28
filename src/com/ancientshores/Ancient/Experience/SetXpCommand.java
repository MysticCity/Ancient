package com.ancientshores.Ancient.Experience;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetXpCommand
{
  public static void setXp(CommandSender cs, String[] args)
  {
    String playername = "";
    if (((cs instanceof Player)) && (!cs.hasPermission("Ancient.XP.Admin")))
    {
      cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "You don't have the permission to use this command");
      return;
    }
    if (args.length < 2)
    {
      cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "Correct usage is level setxp <amount> [player]");
      return;
    }
    if (args.length == 2)
    {
      if (!(cs instanceof Player))
      {
        cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "Correct usage is level setxp amount player");
        return;
      }
      playername = cs.getName();
    }
    if (args.length == 3) {
      playername = args[2];
    }
    int amount;
    try
    {
      amount = Integer.parseInt(args[1]);
    }
    catch (Exception e)
    {
      cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "Expected Integer, recieved string");
      return;
    }
    PlayerData pd = PlayerData.playerHasPlayerData(((Player)cs).getUniqueId());
    if (pd == null) {
      cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "Player not found");
    }
    if (pd != null)
    {
      pd.getXpSystem().xp = amount;
      pd.getXpSystem().addXP(0, false);
      cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "Successfully set the experience of the player " + playername + " to  " + amount);
    }
  }
}
