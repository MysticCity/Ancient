package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.GuildBrand;

public class GuildCommandDeposit {
    public static void processDeposit(CommandSender sender, String[] args) {
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
                    if (Ancient.economy.has(mPlayer.getName(), value)) {
                        Ancient.economy.withdrawPlayer(mPlayer.getName(), value);
                        Ancient.economy.bankDeposit(mGuild.accountName, value);
                        mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.GREEN + "Successfully deposit " + value + " to the bank of your guild.");
                    } else {
                        mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You don't have enough money.");
                    }
                } else {
                    mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You must enter a value bigger than 0.");
                }
            } else {
                mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You aren't in a guild.");
            }
        } else {
            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "Correct usage: /guild deposit <value>");
        }
    }
}