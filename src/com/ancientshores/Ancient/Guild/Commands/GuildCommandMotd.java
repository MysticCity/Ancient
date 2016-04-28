package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandMotd
{
  public static void processMotd(CommandSender sender)
  {
    Player p = (Player)sender;
    AncientGuild guild = AncientGuild.getPlayersGuild(p.getUniqueId());
    if (guild != null)
    {
      if ((guild.motd != null) && (!guild.motd.equals(""))) {
        p.sendMessage(ChatColor.GREEN + "<Guild> motd: " + guild.motd);
      } else {
        p.sendMessage(ChatColor.RED + "Your guild has no motd.");
      }
    }
    else {
      p.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandMotd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */