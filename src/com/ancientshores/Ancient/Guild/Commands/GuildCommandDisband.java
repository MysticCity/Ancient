package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandDisband
{
  public static void processDisband(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
    if (mGuild != null)
    {
      if (mGuild.gLeader.compareTo(mPlayer.getUniqueId()) == 0) {
        mGuild.disband(false);
      } else {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "Only the leader can disband the guild.");
      }
    }
    else {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
    }
  }
}
