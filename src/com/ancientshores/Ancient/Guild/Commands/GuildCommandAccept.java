package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandAccept
{
  public static void processAccept(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    if (AncientGuild.getPlayersGuild(mPlayer.getUniqueId()) == null)
    {
      if (AncientGuild.invites.containsKey(mPlayer.getUniqueId()))
      {
        if (AncientGuild.guilds.contains(AncientGuild.invites.get(mPlayer.getUniqueId())))
        {
          if (AncientGuild.invites.size() < AncientGuild.maxPlayers)
          {
            ((AncientGuild)AncientGuild.invites.get(mPlayer.getUniqueId())).broadcastMessage(Ancient.brand2 + ChatColor.AQUA + mPlayer.getName() + ChatColor.GREEN + " joined your guild.");
            
            ((AncientGuild)AncientGuild.invites.get(mPlayer.getUniqueId())).addMember(mPlayer.getUniqueId(), AncientGuildRanks.TRIAL);
            mPlayer.sendMessage(ChatColor.GREEN + "Succesfully joined the guild.");
            AncientGuild.writeGuild((AncientGuild)AncientGuild.invites.get(mPlayer.getUniqueId()));
            AncientGuild.invites.remove(mPlayer.getUniqueId());
          }
          else
          {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "This guild is already full.");
            AncientGuild.invites.remove(mPlayer.getUniqueId());
          }
        }
        else
        {
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "This guild doesn't exist anymore.");
          AncientGuild.invites.remove(mPlayer.getUniqueId());
        }
      }
      else {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You weren't invited to a guild.");
      }
    }
    else {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You are already in a guild.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandAccept.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */