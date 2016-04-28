package com.ancientshores.Ancient.Party.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Party.AncientParty;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandToggleFriendlyFire
{
  public static void processFF(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
    if (AncientGuild.canToggleff)
    {
      if (mParty != null)
      {
        if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0)
        {
          mParty.setFriendlyFireEnabled(!mParty.isFriendlyFireEnabled());
          mParty.sendMessage(Ancient.brand2 + ChatColor.BLUE + "Friendly fire is now: " + ChatColor.GREEN + (mParty.isFriendlyFireEnabled() ? "off" : "on"));
        }
        else
        {
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You don't have permission to toggle friendly fire");
        }
      }
      else {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't in a party!");
      }
    }
    else {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "Config disallows toggling ff of parties!");
    }
  }
}
