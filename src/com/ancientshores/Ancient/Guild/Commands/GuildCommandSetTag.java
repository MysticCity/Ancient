package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandSetTag
{
  public static void processSetTag(CommandSender sender, String[] args)
  {
    if (!AncientGuild.tagenabled) {
      return;
    }
    Player mPlayer = (Player)sender;
    if (args.length >= 2)
    {
      AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
      if (mGuild != null)
      {
        if (AncientGuildRanks.hasMotdRights((AncientGuildRanks)mGuild.gMember.get(mPlayer.getUniqueId())))
        {
          args[0] = "";
          mGuild.setTag(Ancient.convertStringArrayToString(args).trim());
          AncientGuild.writeGuilds();
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Guild tag successfully set!");
        }
      }
      else {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You are in no guild.");
      }
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "Correct usage: /guild settag <tag>");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandSetTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */