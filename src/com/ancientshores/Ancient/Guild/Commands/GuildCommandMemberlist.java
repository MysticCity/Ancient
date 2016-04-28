package com.ancientshores.Ancient.Guild.Commands;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandMemberlist
{
  public static void processMemberList(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    AncientGuild g = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
    if (g != null)
    {
      Set<UUID> uuids = g.gMember.keySet();
      mPlayer.sendMessage(ChatColor.GREEN + g.guildName + ":");
      for (UUID uuid : uuids) {
        mPlayer.sendMessage(AncientGuildRanks.getChatColorByRank((AncientGuildRanks)g.gMember.get(uuid)) + PlayerFinder.getPlayerName(uuid));
      }
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandMemberlist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */