package com.ancientshores.Ancient.Guild.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import java.util.HashMap;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandMoney
{
  public static void processMoney(CommandSender sender)
  {
    Player mPlayer = (Player)sender;
    AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
    if (mGuild != null)
    {
      if (mGuild.gMember.get(mPlayer.getUniqueId()) != AncientGuildRanks.TRIAL) {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Your guild has " + Ancient.economy.format(Ancient.economy.bankBalance(mGuild.accountName).balance));
      } else {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You must be at least a Member to do that.");
      }
    }
    else {
      mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You arn't in a guild.");
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandMoney.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */