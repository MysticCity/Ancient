package com.ancientshores.Ancient.Party.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;
import java.util.Collection;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandIgnore
{
  public static void processIgnore(CommandSender sender, String[] args)
  {
    Player mPlayer = (Player)sender;
    if (args.length == 0)
    {
      if (!AncientParty.mIgnoreList.contains(mPlayer.getUniqueId()))
      {
        AncientParty.mIgnoreList.add(mPlayer.getUniqueId());
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You are ignoring all invitations now.");
      }
      else
      {
        AncientParty.mIgnoreList.remove(mPlayer.getUniqueId());
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't ignoring invitations anymore.");
      }
    }
    else {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "Correct usage: /pignore");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Party\Commands\PartyCommandIgnore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */