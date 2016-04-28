package com.ancientshores.Ancient.Party.Commands;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandGrant
{
  public static void processGrant(CommandSender sender, String[] args, Ancient main)
  {
    Player mPlayer = (Player)sender;
    if (args.length == 2)
    {
      AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
      if (mParty != null)
      {
        if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0)
        {
          if (Bukkit.getPlayer(args[1]) != null)
          {
            if (Bukkit.getPlayer(args[1]).hasPermission("Ancient.party.create"))
            {
              mParty.setLeader(Bukkit.getPlayer(args[1]).getUniqueId());
              mParty.sendMessage(Ancient.brand2 + ChatColor.GREEN + PlayerFinder.getPlayerName(mParty.getLeader()) + ChatColor.BLUE + " is the new leader of the party.");
              mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "Succesfully granted " + ChatColor.GOLD + PlayerFinder.getPlayerName(mParty.getLeader()) + ChatColor.BLUE + " leader rights.");
            }
            else
            {
              mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "The player you chose doesn't have the permissions to lead a party.");
            }
          }
          else {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "The player you invited doesn't exist.");
          }
        }
        else {
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't the leader of this party.");
        }
      }
      else {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't in a party.");
      }
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "Correct usage: /pgrant <name>");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Party\Commands\PartyCommandGrant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */