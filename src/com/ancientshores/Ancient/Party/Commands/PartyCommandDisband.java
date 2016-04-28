package com.ancientshores.Ancient.Party.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;
import java.util.Collection;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandDisband
{
  public static void processDisband(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
    if (mParty != null)
    {
      if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0)
      {
        mParty.removeAll();
        AncientParty.partys.remove(mParty);
      }
      else
      {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't the leader of this party.");
      }
    }
    else {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't in a party.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Party\Commands\PartyCommandDisband.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */