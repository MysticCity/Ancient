package com.ancientshores.Ancient.Guild.Commands;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandList
{
  public static void processList(CommandSender sender, String[] args)
  {
    Player mPlayer = (Player)sender;
    if (args[0].equalsIgnoreCase("list"))
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "List of guilds:");
      for (AncientGuild g : AncientGuild.guilds) {
        mPlayer.sendMessage(ChatColor.GREEN + g.guildName + " " + ChatColor.DARK_RED + PlayerFinder.getPlayerName(g.gLeader) + ChatColor.GREEN + " (" + g.gMember.size() + ")");
      }
    }
  }
}
