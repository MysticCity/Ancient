package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandReject
{
  public static void processReject(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    if (AncientGuild.invites.containsKey(mPlayer.getUniqueId()))
    {
      AncientGuild.invites.remove(mPlayer.getUniqueId());
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Succesfully rejected the guild invitation.");
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You weren't invited to a guild.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandReject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */