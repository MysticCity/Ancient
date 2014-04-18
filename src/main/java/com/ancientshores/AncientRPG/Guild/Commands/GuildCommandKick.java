package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandKick {
    public static void processKick(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(mPlayer.getName());
            if (guild != null) {
                AncientRPGGuildRanks rank = guild.gMember.get(mPlayer.getName());
                if (guild.gMember.containsKey(args[1])) {
                    if (rank == AncientRPGGuildRanks.LEADER) {
                        if (!(guild.gMember.get(args[1]) == AncientRPGGuildRanks.LEADER)) {
                            guild.kickMember(args[1]);
                            AncientRPGGuild.writeGuild(AncientRPGGuild.invites.get(mPlayer));
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You cannot kick yourself as a Leader.");
                        }
                    } else if (rank == AncientRPGGuildRanks.CO_LEADER) {
                        if (!(guild.gMember.get(args[1]) == AncientRPGGuildRanks.LEADER) && !(guild.gMember.get(args[1]) == AncientRPGGuildRanks.CO_LEADER)) {
                            guild.kickMember(args[1]);
                            AncientRPGGuild.writeGuild(AncientRPGGuild.invites.get(mPlayer));
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