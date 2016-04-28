package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandInfo
{
  public static void processInfo(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    AncientGuild guild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
    if (guild != null)
    {
      mPlayer.sendMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
      mPlayer.sendMessage(ChatColor.GREEN + "Guild info:");
      Set<UUID> guildplayers = guild.gMember.keySet();
      int online = 0;
      for (UUID uuid : guildplayers)
      {
        Player p = Bukkit.getPlayer(uuid);
        if ((p != null) && (p.isOnline())) {
          online++;
        }
      }
      mPlayer.sendMessage("Members in your guild: " + guild.gMember.size());
      mPlayer.sendMessage("Currently online: " + online);
      if ((AncientGuild.economyenabled) && (Ancient.economy != null)) {
        mPlayer.sendMessage("Money of your guild: " + Ancient.economy.getBalance(guild.accountName));
      }
      mPlayer.sendMessage("To see a list of all players online type /guild online");
      mPlayer.sendMessage("To see a list of all members type /guild memberlist");
      mPlayer.sendMessage(ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------");
    }
    else
    {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
    }
  }
}
