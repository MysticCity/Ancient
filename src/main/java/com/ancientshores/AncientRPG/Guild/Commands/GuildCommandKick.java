package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;

public class GuildCommandKick {
    @SuppressWarnings("deprecation")
	public static void processKick(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(mPlayer.getUniqueId());
            if (guild != null) {
                AncientRPGGuildRanks rank = guild.gMember.get(mPlayer.getUniqueId());
                if (guild.gMember.containsKey(Bukkit.getOfflinePlayer(args[1]).getUniqueId())) {
                    if (rank == AncientRPGGuildRanks.LEADER) {
                        if (!(guild.gMember.get(Bukkit.getOfflinePlayer(args[1]).getUniqueId()) == AncientRPGGuildRanks.LEADER)) {
                            guild.kickMember(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                            AncientRPGGuild.writeGuild(guild);//AncientRPGGuild.invites.get(mPlayer.getUniqueId()));
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You cannot kick yourself as a Leader.");
                        }
                    } else if (rank == AncientRPGGuildRanks.CO_LEADER) {
                        if (!(guild.gMember.get(Bukkit.getOfflinePlayer(args[1]).getUniqueId()) == AncientRPGGuildRanks.LEADER) && !(guild.gMember.get(Bukkit.getOfflinePlayer(args[1]).getUniqueId()) == AncientRPGGuildRanks.CO_LEADER)) {
                            guild.kickMember(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                            AncientRPGGuild.writeGuild(guild);//AncientRPGGuild.invites.get(mPlayer.getUniqueId()));
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are not allowed to kick this player.");
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You don't have the required rank to kick a Player.");
                    }
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "This player isn't in your guild or does not exist, please use the exact name.");
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "Correct usage /guild kick <name>");
        }
    }
}