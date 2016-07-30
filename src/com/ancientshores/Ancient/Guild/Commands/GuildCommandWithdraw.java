package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import com.ancientshores.Ancient.Guild.GuildBrand;

public class GuildCommandWithdraw {
    public static void processWithdraw(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
            if (mGuild != null) {
                Double value;
                try {
                    value = Double.parseDouble(args[1]);
                } catch (Exception e) {
                    mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "String given, Decimal expected");
                    return;
                }
                if (value > 0) {
                    if (Ancient.economy.bankHas(mGuild.accountName, value).transactionSuccess()) {
                        if (mGuild.gMember.get(mPlayer.getUniqueId()) == AncientGuildRanks.LEADER || mGuild.gMember.get(mPlayer.getUniqueId()) == AncientGuildRanks.CO_LEADER) {
                            Ancient.economy.bankWithdraw(mGuild.accountName, value);
                            Ancient.economy.depositPlayer(mPlayer.getName(), value);
                            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.GREEN + "Successfully withdraw " + value + " of the bank of your guild.");
                        } else {
                            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You must be a (co-)leader to do that.");
                        }
                    } else {
                        mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "Your guild doesn't have enough money.");
                    }
                } else {
                    mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You must enter a value bigger than 0");
                }
            } else {
                mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You are in no guild.");
            }
        }
    }
}