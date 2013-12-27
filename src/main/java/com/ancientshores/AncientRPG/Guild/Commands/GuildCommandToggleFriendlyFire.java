package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandToggleFriendlyFire {
    public static void processFF(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer
                .getName());
        if (AncientRPGGuild.canToggleff) {
            if (mGuild != null) {
                AncientRPGGuildRanks r = mGuild.getgMember().get(
                        mPlayer.getName());
                if (r == AncientRPGGuildRanks.CO_LEADER
                        || r == AncientRPGGuildRanks.LEADER) {
                    if (mGuild.friendlyFire) {
                        mGuild.friendlyFire = false;
                        mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN
                                + "Friendly fire is now: " + ChatColor.GREEN
                                + "off");
                    } else {
                        mGuild.friendlyFire = true;
                        mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN
                                + "Friendly fire is now: " + ChatColor.RED
                                + "on");
                    }
                    AncientRPGGuild.writeGuild(mGuild);
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
                            + "You don't have permission to toggle friendly fire");
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "You aren't in a guild!");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "Config disallows toggling ff of guilds!");
        }
    }
}
