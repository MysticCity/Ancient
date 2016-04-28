package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandSetMotd
{
  public static void processSetMOTD(CommandSender sender, String[] args)
  {
    Player player = (Player)sender;
    if (args.length >= 2)
    {
      AncientGuild guild = AncientGuild.getPlayersGuild(player.getUniqueId());
      if (guild != null)
      {
        if (AncientGuildRanks.hasMotdRights((AncientGuildRanks)guild.gMember.get(player.getUniqueId())))
        {
          args[0] = "";
          guild.motd = Ancient.convertStringArrayToString(args);
          guild.broadcastMessage(Ancient.brand2 + ChatColor.GREEN + "<Guild> motd: " + guild.motd);
          AncientGuild.writeGuilds();
        }
        else
        {
          player.sendMessage(Ancient.brand2 + ChatColor.RED + "You do not have the rights to change the MOTD.");
        }
      }
      else {
        player.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
      }
    }
    else
    {
      player.sendMessage(Ancient.brand2 + ChatColor.RED + "Correct usage: /guild setmotd <message>");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandSetMotd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */