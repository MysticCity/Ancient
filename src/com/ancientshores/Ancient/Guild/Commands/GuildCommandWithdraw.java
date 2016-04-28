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

public class GuildCommandWithdraw
{
  public static void processWithdraw(CommandSender sender, String[] args)
  {
    Player mPlayer = (Player)sender;
    if (args.length == 2)
    {
      AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
      if (mGuild != null)
      {
        Double value;
        try
        {
          value = Double.valueOf(Double.parseDouble(args[1]));
        }
        catch (Exception e)
        {
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "String given, Decimal expected");
          return;
        }
        if (value.doubleValue() > 0.0D)
        {
          if (Ancient.economy.bankHas(mGuild.accountName, value.doubleValue()).transactionSuccess())
          {
            if ((mGuild.gMember.get(mPlayer.getUniqueId()) == AncientGuildRanks.LEADER) || (mGuild.gMember.get(mPlayer.getUniqueId()) == AncientGuildRanks.CO_LEADER))
            {
              Ancient.economy.bankWithdraw(mGuild.accountName, value.doubleValue());
              Ancient.economy.depositPlayer(mPlayer.getName(), value.doubleValue());
              mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Successfully withdraw " + value + " of the bank of your guild.");
            }
            else
            {
              mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You must be a (co-)leader to do that.");
            }
          }
          else {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "Your guild doesn't have enough money.");
          }
        }
        else {
          mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You must enter a value bigger than 0");
        }
      }
      else
      {
        mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You are in no guild.");
      }
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Guild\Commands\GuildCommandWithdraw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */