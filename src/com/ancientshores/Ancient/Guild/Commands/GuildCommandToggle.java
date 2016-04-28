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
