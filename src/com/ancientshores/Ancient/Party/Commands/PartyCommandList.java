package com.ancientshores.Ancient.Party.Commands;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandList
{
  public static void processList(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    if (AncientParty.getPlayersParty(mPlayer.getUniqueId()) != null)
    {
      if (mPlayer.hasPermission(""))
      {
        AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "In your party are:");
        for (UUID uuid : mParty.getMembers()) {
          if (uuid != null) {
            if (uuid == AncientParty.getPlayersParty(mPlayer.getUniqueId()).getLeader()) {
              mPlayer.sendMessage(ChatColor.GREEN + PlayerFinder.getPlayerName(uuid) + "(Leader)");
            } else {
              mPlayer.sendMessage(ChatColor.GOLD + PlayerFinder.getPlayerName(uuid));
            }
          }
        }
      }
      else
      {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You don't have the permissions to list the party members.");
      }
    }
    else {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't in a party.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Party\Commands\PartyCommandList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */