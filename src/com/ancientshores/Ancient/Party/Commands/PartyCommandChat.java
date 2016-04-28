package com.ancientshores.Ancient.Party.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Party.AncientParty;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandChat
{
  public static void processChat(CommandSender sender, String[] args)
  {
    Player mPlayer = (Player)sender;
    AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
    if (mParty == null)
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.BLUE + "You aren't in a party.");
    }
    else
    {
      String[] message = new String[args.length - 1];
      System.arraycopy(args, 1, message, 0, args.length - 1);
      mParty.sendMessage(message, mPlayer);
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Party\Commands\PartyCommandChat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */