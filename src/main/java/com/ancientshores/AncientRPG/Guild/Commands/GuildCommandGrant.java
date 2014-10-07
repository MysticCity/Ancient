package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;

public class GuildCommandGrant {
    @SuppressWarnings("deprecation")
	public static void processGrant(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length == 3) {
            AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer.getUniqueId());
            if (mGuild != null) {
                Player grantedplayer = Bukkit.getPlayer(args[1]);
                if (grantedplayer != null && grantedplayer.isOnline() && mGuild.gMember.containsKey(grantedplayer.getUniqueId())) {
                    AncientRPGGuildRanks newrank = AncientRPGGuildRanks.getRankByString(args[2]);
                    if (newrank != null) {
                        switch (mGuild.gMember.get(mPlayer.getUniqueId())) {
                            case LEADER:
                                if (newrank == AncientRPGGuildRanks.LEADER && grantedplayer != mPlayer) {
                                    mGuild.gLeader = grantedplayer.getUniqueId();
                                    mGuild.gMember.put(grantedplayer.getUniqueId(), AncientRPGGuildRanks.LEADER);
                                    mGuild.gMember.put(mPlayer.getUniqueId(), AncientRPGGuildRanks.CO_LEADER);
                                    mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + "<Guild>: " + ChatColor.DARK_RED + grantedplayer.getName() + ChatColor.GREEN + " is the new Leader of the guild.");
                                    return;
                                } else if (grantedplayer != mPlayer) {
                                    mGuild.gMember.put(grantedplayer.getUniqueId(), newrank);
                                    mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + "<Guild>: " + AncientRPGGuildRanks.getChatColorByRank(newrank) + grantedplayer.getName() + AncientRPGGuildRanks.getChatColorByRank(newrank) + ChatColor.GREEN + " is now a " + newrank.getGuildRank() + ".");
                                }
                                return;
                            case CO_LEADER:
                                if (newrank == AncientRPGGuildRanks.MEMBER || newrank == AncientRPGGuildRanks.OFFICER || newrank == AncientRPGGuildRanks.TRIAL) {
                                    mGuild.gMember.put(grantedplayer.getUniqueId(), newrank);
                                    mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + "<Guild>: " + grantedplayer.getName() + AncientRPGGuildRanks.getChatColorByRank(newrank) + ChatColor.GREEN + " is now a " + newrank.getGuildRank() + ".");

                                } else {
                                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "You haven't the rights to do that.");
                                }
                                return;
                            case OFFICER:
                                if (newrank == AncientRPGGuildRanks.MEMBER || newrank == AncientRPGGuildRanks.OFFICER || newrank == AncientRPGGuildRanks.TRIAL) {
                                    mGuild.gMember.put(grantedplayer.getUniqueId(), newrank);
                                    mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + "<Guild>: " + grantedplayer.getName() + AncientRPGGuildRanks.getChatColorByRank(newrank) + ChatColor.GREEN + " is now a " + newrank.getGuildRank() + ".");
                                } else {
                                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "You haven't the rights to do that.");
                                }
                                return;
                            default:
                                break;
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "This rank doesn't exist.");
                    }
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "This player doesn't exist, isn't in your guild or isn't online.");
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are in no guild");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "Correct usage /guild grant <name> <rank>");
        }
    }
}