package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandLeave
{
  public static void processLeave(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    AncientGuild guild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
    if (guild != null)
    {
      ChatColor farbe = AncientGuildRanks.getChatColorByRank((AncientGuildRanks)guild.getGuildMembers().get(mPlayer.getUniqueId()));
      guild.gMember.remove(mPlayer.getUniqueId());
      AncientGuild.writeGuild(guild);
      guild.broadcastMessage(Ancient.brand2 + farbe + mPlayer.getName() + ChatColor.GREEN + " left the guild.");
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Succesfully left your guild.");
      mPlayer.setDisplayName(mPlayer.getName());
      if ((mPlayer.getUniqueId().compareTo(guild.gLeader) == 0) && (guild.gMember.size() > 0)) {
        mPlayer.sendMessage(Ancient.brand2 + "You can't leave the guild as a leader unless you are the only one in the guild.");
      }
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandLeave.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */