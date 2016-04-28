package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandCreate
{
  public static boolean isValidGuildname(String Name)
  {
    Pattern pattern = Pattern.compile("^[A-Za-z]{3,15}$");
    Matcher matcher = pattern.matcher(Name);
    
    return matcher.matches();
  }
  
  public static void processCreate(CommandSender sender, String[] args)
  {
    Player mPlayer = (Player)sender;
    if (mPlayer.hasPermission("Ancient.guild.create"))
    {
      if (AncientGuild.getPlayersGuild(mPlayer.getUniqueId()) == null)
      {
        args[0] = "";
        String name = Ancient.convertStringArrayToString((String[])Arrays.copyOfRange(args, 1, args.length));
        if (!isValidGuildname(name))
        {
          sender.sendMessage(Ancient.brand2 + ChatColor.RED + "The guildname has invalid characters (only A-Z) or is too long/short");
          return;
        }
        if (!AncientGuild.guildExists(name))
        {
          if (Ancient.iConomyEnabled()) {
            if (Ancient.economy.has(mPlayer.getName(), AncientGuild.cost))
            {
              Ancient.economy.withdrawPlayer(mPlayer.getName(), AncientGuild.cost);
            }
            else
            {
              mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You don't have enough money to create a guild");
              return;
            }
          }
          AncientGuild.guilds.add(new AncientGuild(name, mPlayer.getUniqueId()));
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Succesfully created a new Guild");
        }
        else
        {
          sender.sendMessage(Ancient.brand2 + ChatColor.RED + "A guild with that name already exists.");
        }
      }
      else
      {
        sender.sendMessage(Ancient.brand2 + ChatColor.RED + "You are already in a guild.");
      }
    }
    else {
      sender.sendMessage(Ancient.brand2 + ChatColor.RED + "You don't have the permissions to create a guild");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandCreate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */