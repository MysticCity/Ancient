package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import com.ancientshores.Ancient.Guild.GuildBrand;

public class GuildCommandMoney {
    public static void processMoney(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
        if (mGuild != null) {
            if (mGuild.gMember.get(mPlayer.getUniqueId()) != AncientGuildRanks.TRIAL) {
                mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.GREEN + "Your guild has " + Ancient.economy.format(Ancient.economy.bankBalance(mGuild.accountName).balance));
            } else {
                mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You must be at least a Member to do that.");
            }
        } else {
            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You arn't in a guild.");
        }
    }
}