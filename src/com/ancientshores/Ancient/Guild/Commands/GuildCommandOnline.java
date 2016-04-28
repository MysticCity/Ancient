package com.ancientshores.Ancient.Guild.Commands;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandOnline
{
  public static void processOnline(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    AncientGuild guild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
    if (guild != null)
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Guild members online:");
      Set<UUID> uuids = guild.gMember.keySet();
      for (UUID uuid : uuids) {
        if ((Bukkit.getPlayer(uuid) != null) && (Bukkit.getPlayer(uuid).isOnline())) {
          mPlayer.sendMessage(AncientGuildRanks.getChatColorByRank((AncientGuildRanks)guild.gMember.get(uuid)) + PlayerFinder.getPlayerName(uuid));
        }
      }
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandOnline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */