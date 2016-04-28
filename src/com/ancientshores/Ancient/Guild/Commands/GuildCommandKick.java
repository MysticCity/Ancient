package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandKick
{
  public static void processKick(CommandSender sender, String[] args)
  {
    Player mPlayer = (Player)sender;
    if (args.length == 2)
    {
      AncientGuild guild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
      if (guild != null)
      {
        AncientGuildRanks rank = (AncientGuildRanks)guild.gMember.get(mPlayer.getUniqueId());
        if (guild.gMember.containsKey(Bukkit.getOfflinePlayer(args[1]).getUniqueId()))
        {
          if (rank == AncientGuildRanks.LEADER)
          {
            if (guild.gMember.get(Bukkit.getOfflinePlayer(args[1]).getUniqueId()) != AncientGuildRanks.LEADER)
            {
              guild.kickMember(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
              AncientGuild.writeGuild(guild);
            }
            else
            {
              mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You cannot kick yourself as a Leader.");
            }
          }
          else if (rank == AncientGuildRanks.CO_LEADER)
          {
            if ((guild.gMember.get(Bukkit.getOfflinePlayer(args[1]).getUniqueId()) != AncientGuildRanks.LEADER) && (guild.gMember.get(Bukkit.getOfflinePlayer(args[1]).getUniqueId()) != AncientGuildRanks.CO_LEADER))
            {
              guild.kickMember(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
              AncientGuild.writeGuild(guild);
            }
            else
            {
              mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You are not allowed to kick this player.");
            }
          }
          else {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You don't have the required rank to kick a Player.");
          }
        }
        else {
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "This player isn't in your guild or does not exist, please use the exact name.");
        }
      }
      else
      {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
      }
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "Correct usage /guild kick <name>");
    }
  }
}
