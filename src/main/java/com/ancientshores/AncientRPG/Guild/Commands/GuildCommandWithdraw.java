package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandWithdraw {
    public static void processWithdraw(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer
                    .getName());
            if (mGuild != null) {
                Double value;
                try {
                    value = Double.parseDouble(args[1]);
                } catch (Exception e) {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                            + "String given, Decimal expected");
                    return;
                }
                if (value > 0) {
                    if (AncientRPG.economy.bankHas(mGuild.accountName, value)
                            .transactionSuccess()) {
                        if (mGuild.gMember.get(mPlayer.getName()) == AncientRPGGuildRanks.LEADER
                                || mGuild.gMember.get(mPlayer.getName()) == AncientRPGGuildRanks.CO_LEADER) {
                            AncientRPG.economy.bankWithdraw(mGuild.accountName,
                                    value);
                            AncientRPG.economy.depositPlayer(mPlayer.getName(),
                                    value);
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
                                    + "Successfully withdraw " + value
                                    + " of the bank of your guild.");
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                    + "You must be a (co-)leader to do that.");
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                + "Your guild doesn't have enough money.");
                    }
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                            + "You must enter a value bigger than 0");
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are in no guild.");
            }
        }
    }
}