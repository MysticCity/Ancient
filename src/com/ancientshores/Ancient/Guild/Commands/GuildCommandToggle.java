package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Listeners.AncientPlayerListener;
import java.util.Collection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandToggle
{
  public static void processToggle(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    if (!AncientPlayerListener.toggleguildlist.contains(mPlayer.getUniqueId()))
    {
      AncientPlayerListener.toggleguildlist.add(mPlayer.getUniqueId());
      mPlayer.sendMessage(Ancient.brand2 + "Guild chat activated");
    }
    else
    {
      AncientPlayerListener.toggleguildlist.remove(mPlayer.getUniqueId());
      mPlayer.sendMessage(Ancient.brand2 + "Guild chat deactivated");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandToggle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */