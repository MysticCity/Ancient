package com.ancientshores.Ancient.Party.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandKick
{
  public static void processKick(CommandSender sender, String[] args, Ancient main)
  {
    Player mPlayer = (Player)sender;
    if (args.length == 2)
    {
      AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
      if (mParty != null)
      {
        if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0)
        {
          Player mKickedPlayer = main.getServer().getPlayer(args[1]);
          mParty.removePlayer(mKickedPlayer.getUniqueId());
          mParty.sendMessage(Ancient.brand2 + ChatColor.GOLD + mKickedPlayer.getName() + ChatColor.BLUE + " was kicked out of your party.");
          mKickedPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You were kicked out of the party.");
        }
        else
        {
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't the leader of the party.");
        }
      }
      else {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't in a party.");
      }
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "Correct usage: /pkick <name>");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Party\Commands\PartyCommandKick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */