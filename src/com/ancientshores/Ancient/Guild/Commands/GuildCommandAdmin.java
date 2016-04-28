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

public class GuildCommandAdmin
{
  public static void processAdmin(CommandSender sender, String[] args)
  {
    Player mPlayer = (Player)sender;
    if (mPlayer.hasPermission("Ancient.guild.admin"))
    {
      if (args.length == 2)
      {
        if (args[1].equalsIgnoreCase("save")) {
          AncientGuild.writeGuilds();
        }
      }
      else if (args.length == 3) {
        if (args[1].equals("db"))
        {
          AncientGuild g = AncientGuild.getPlayersGuild(Bukkit.getPlayer(args[2]).getUniqueId());
          if (g != null) {
            g.disband(true);
          } else {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "This player does not exist, please use the exact name.");
          }
        }
        else if (args[1].equals("list"))
        {
          AncientGuild g = AncientGuild.getPlayersGuild(Bukkit.getPlayer(args[2]).getUniqueId());
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
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "This player does not exist, please use the exact name.");
          }
        }
      }
    }
    else {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't a guild admin.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */