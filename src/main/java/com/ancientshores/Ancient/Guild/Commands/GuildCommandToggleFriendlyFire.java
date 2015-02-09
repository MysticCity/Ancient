package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;

public class GuildCommandToggleFriendlyFire {
    public static void processFF(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
        if (AncientGuild.canToggleff) {
            if (mGuild != null) {
                AncientGuildRanks r = mGuild.getGuildMembers().get(mPlayer.getUniqueId());
                if (r == AncientGuildRanks.CO_LEADER || r == AncientGuildRanks.LEADER) {
                    if (mGuild.friendlyFire) {
                        mGuild.friendlyFire = false;
                        mGuild.broadcastMessage(Ancient.brand2 + ChatColor.GREEN + "Friendly fire is now: " + ChatColor.GREEN + "off");
                    } else {
                        mGuild.friendlyFire = true;
                        mGuild.broadcastMessage(Ancient.brand2 + ChatColor.GREEN + "Friendly fire is now: " + ChatColor.RED + "on");
                    }
                    AncientGuild.writeGuild(mGuild);
                } else {
                    mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "You don't have permission to toggle friendly fire");
                }
            } else {
                mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "You aren't in a guild!");
            }
        } else {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Config disallows toggling ff of guilds!");
        }
    }
}