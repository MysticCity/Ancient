package com.ancientshores.Ancient.Party.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandAccept
{
  public static void processAccept(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    if (AncientParty.invites.containsKey(mPlayer.getUniqueId()))
    {
      if (((AncientParty)AncientParty.invites.get(mPlayer.getUniqueId())).getMemberNumber() < AncientParty.maxPlayers)
      {
        AncientParty inviteParty = (AncientParty)AncientParty.invites.get(mPlayer.getUniqueId());
        if ((inviteParty != null) && (inviteParty.getMemberNumber() > 0))
        {
          inviteParty.sendMessage(Ancient.brand2 + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + " joined your party.");
          ((AncientParty)AncientParty.invites.get(mPlayer.getUniqueId())).addPlayer(mPlayer.getUniqueId());
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You joined a party.");
        }
        else
        {
          AncientParty.invites.remove(mPlayer.getUniqueId());
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "The party you tried to join doesn't exist anymore.");
        }
      }
      else
      {
        AncientParty.invites.remove(mPlayer.getUniqueId());
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "This party is already full.");
      }
      AncientParty.invites.remove(mPlayer.getUniqueId());
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You weren't invited in a party.");
    }
  }
}
