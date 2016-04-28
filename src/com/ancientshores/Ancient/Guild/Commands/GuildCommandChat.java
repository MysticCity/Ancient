package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandChat
{
  public static void processChat(CommandSender sender, String[] args)
  {
    Player mPlayer = (Player)sender;
    if (args.length >= 2)
    {
      AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
      if (mGuild != null)
      {
        args[0] = "";
        mGuild.sendMessage(Ancient.convertStringArrayToString(args), mPlayer);
      }
      else
      {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You are in no guild.");
      }
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "Correct usage: /guild chat <message> or /gs <message> or /gc <message>");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandChat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */