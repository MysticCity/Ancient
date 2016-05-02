package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;

public class GuildCommandGrant {
    @SuppressWarnings("deprecation")
	public static void processGrant(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length == 3) {
            AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
            if (mGuild != null) {
                Player grantedplayer = Bukkit.getPlayer(args[1]);
                if (grantedplayer != null && grantedplayer.isOnline() && mGuild.gMember.containsKey(grantedplayer.getUniqueId())) {
                    AncientGuildRanks newrank = AncientGuildRanks.getRankByString(args[2]);
                    if (newrank != null) {
                        switch (mGuild.gMember.get(mPlayer.getUniqueId())) {
                            case LEADER:
                                if (newrank == AncientGuildRanks.LEADER && grantedplayer != mPlayer) {
                                    mGuild.gLeader = grantedplayer.getUniqueId();
                                    mGuild.gMember.put(grantedplayer.getUniqueId(), AncientGuildRanks.LEADER);
                                    mGuild.gMember.put(mPlayer.getUniqueId(), AncientGuildRanks.CO_LEADER);
                                    mGuild.broadcastMessage(Ancient.ChatBrand + ChatColor.GREEN + "<Guild>: " + ChatColor.DARK_RED + grantedplayer.getName() + ChatColor.GREEN + " is the new Leader of the guild.");
                                    return;
                                } else if (grantedplayer != mPlayer) {
                                    mGuild.gMember.put(grantedplayer.getUniqueId(), newrank);
                                    mGuild.broadcastMessage(Ancient.ChatBrand + ChatColor.GREEN + "<Guild>: " + AncientGuildRanks.getChatColorByRank(newrank) + grantedplayer.getName() + AncientGuildRanks.getChatColorByRank(newrank) + ChatColor.GREEN + " is now a " + newrank.getGuildRank() + ".");
                                }
                                return;
                            case CO_LEADER:
                                if (newrank == AncientGuildRanks.MEMBER || newrank == AncientGuildRanks.OFFICER || newrank == AncientGuildRanks.TRIAL) {
                                    mGuild.gMember.put(grantedplayer.getUniqueId(), newrank);
                                    mGuild.broadcastMessage(Ancient.ChatBrand + ChatColor.GREEN + "<Guild>: " + grantedplayer.getName() + AncientGuildRanks.getChatColorByRank(newrank) + ChatColor.GREEN + " is now a " + newrank.getGuildRank() + ".");

                                } else {
                                    mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.GREEN + "You haven't the rights to do that.");
                                }
                                return;
                            case OFFICER:
                                if (newrank == AncientGuildRanks.MEMBER || newrank == AncientGuildRanks.OFFICER || newrank == AncientGuildRanks.TRIAL) {
                                    mGuild.gMember.put(grantedplayer.getUniqueId(), newrank);
                                    mGuild.broadcastMessage(Ancient.ChatBrand + ChatColor.GREEN + "<Guild>: " + grantedplayer.getName() + AncientGuildRanks.getChatColorByRank(newrank) + ChatColor.GREEN + " is now a " + newrank.getGuildRank() + ".");
                                } else {
                                    mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.GREEN + "You haven't the rights to do that.");
                                }
                                return;
                            default:
                                break;
                        }
                    } else {
                        mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "This rank doesn't exist.");
                    }
                } else {
                    mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "This player doesn't exist, isn't in your guild or isn't online.");
                }
            } else {
                mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "You are in no guild");
            }
        } else {
            mPlayer.sendMessage(Ancient.ChatBrand + ChatColor.RED + "Correct usage /guild grant <name> <rank>");
        }
    }
}