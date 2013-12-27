package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandMoney {
    public static void processMoney(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer.getName());
        if (mGuild != null) {
            if (mGuild.gMember.get(mPlayer.getName()) != AncientRPGGuildRanks.TRIAL) {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "Your guild has " + AncientRPG.economy.format(AncientRPG.economy.bankBalance(mGuild.accountName).balance));
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You must be at least a Member to do that.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You arn't in a guild.");
        }
    }
}
