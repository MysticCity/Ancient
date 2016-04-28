package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandInvite
{
  public static void processInvite(CommandSender sender, String[] args)
  {
    Player player = (Player)sender;
    if (args.length == 2)
    {
      AncientGuild guild = AncientGuild.getPlayersGuild(player.getUniqueId());
      if (guild != null)
      {
        if (AncientGuildRanks.hasInviteRights((AncientGuildRanks)guild.gMember.get(player.getUniqueId())))
        {
          Player invitedPlayer = Bukkit.getPlayer(args[1]);
          if ((invitedPlayer != null) && (AncientGuild.getPlayersGuild(invitedPlayer.getUniqueId()) == null) && (!AncientGuild.invites.containsKey(invitedPlayer.getUniqueId())))
          {
            if (invitedPlayer.hasPermission("Ancient.guild.join"))
            {
              if (AncientGuild.invites.size() < AncientGuild.maxPlayers)
              {
                AncientGuild.invites.put(invitedPlayer.getUniqueId(), guild);
                player.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Invited " + invitedPlayer.getName() + " to your guild.");
                invitedPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "You were invited to the guild " + "\"" + guild.guildName + "\"" + " by " + player.getName() + ".");
                invitedPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Use /guild accept or/guild reject.");
              }
              else
              {
                player.sendMessage(Ancient.brand2 + ChatColor.RED + "Your guild is already full.");
              }
            }
            else {
              player.sendMessage(Ancient.brand2 + ChatColor.RED + "This Player hasn't the permission to join a guild.");
            }
          }
          else {
            player.sendMessage(Ancient.brand2 + ChatColor.RED + "This Player already has a guild or doesn't exist or already is invited.");
          }
        }
        else
        {
          player.sendMessage(Ancient.brand2 + ChatColor.RED + "You don't have the rank to invite people.");
        }
      }
      else {
        player.sendMessage(Ancient.brand2 + ChatColor.RED + "You are in no guild.");
      }
    }
    else
    {
      player.sendMessage(Ancient.brand2 + ChatColor.RED + "Correct usage: /guild invite <player>");
    }
  }
}
