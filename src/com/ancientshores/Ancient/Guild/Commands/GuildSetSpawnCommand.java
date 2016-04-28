package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import com.ancientshores.Ancient.Util.SerializableLocation;
import java.util.HashMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildSetSpawnCommand
{
  public static void setGuildSpawnCommand(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
    if (mGuild != null)
    {
      AncientGuildRanks rank = (AncientGuildRanks)mGuild.gMember.get(mPlayer.getUniqueId());
      if ((rank == AncientGuildRanks.LEADER) || (rank == AncientGuildRanks.CO_LEADER))
      {
        if (AncientGuild.spawnEnabled)
        {
          mGuild.spawnLocation = new SerializableLocation(mPlayer.getLocation());
          sender.sendMessage(Ancient.brand2 + "Successfully set the guildspawn to your current location");
          AncientGuild.writeGuild(mGuild);
        }
        else
        {
          sender.sendMessage(Ancient.brand2 + "The spawn feature is not enabled on this server");
        }
      }
      else {
        sender.sendMessage(Ancient.brand2 + "You have to be atleast a coleader to use this command");
      }
    }
    else
    {
      sender.sendMessage(Ancient.brand2 + "You have to be in a guild to use this command");
    }
  }
}
