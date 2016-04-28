package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandRemoveMotd
{
  public static void processRemoveMOTD(CommandSender sender, String[] args)
  {
    Player player = (Player)sender;
    
    AncientGuild guild = AncientGuild.getPlayersGuild(player.getUniqueId());
    if (guild != null)
    {
      if (AncientGuildRanks.hasMotdRights((AncientGuildRanks)guild.gMember.get(player.getUniqueId())))
      {
        guild.motd = "";
        player.sendMessage(Ancient.brand2 + ChatColor.GREEN + "MOTD was successfully removed!");
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
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandRemoveMotd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */