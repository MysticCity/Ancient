package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandRemoveTag
{
  public static void processRemoveTag(CommandSender sender, String[] args)
  {
    if (!AncientGuild.tagenabled) {
      return;
    }
    Player mPlayer = (Player)sender;
    AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
    if (mGuild != null)
    {
      if (AncientGuildRanks.hasMotdRights((AncientGuildRanks)mGuild.gMember.get(mPlayer.getUniqueId())))
      {
        mGuild.setTag("");
        AncientGuild.writeGuilds();
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Guild tag successfully removed!");
      }
    }
    else {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You are in no guild.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandRemoveTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */