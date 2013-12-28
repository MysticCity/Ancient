package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class GuildCommandAdmin {
    public static void processAdmin(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (AncientRPG.hasPermissions(mPlayer, AncientRPGGuild.gNodeAdmin)) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("save")) {
                    AncientRPGGuild.writeGuilds();
                }

            } else if (args.length == 3) {
                if (args[1].equals("db")) {
                    AncientRPGGuild g = AncientRPGGuild
                            .getPlayersGuild(args[2]);
                    if (g != null) {
                        g.disband(true);
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                + "This player does not exist, please use the exact name.");
                    }
                } else if (args[1].equals("list")) {
                    AncientRPGGuild g = AncientRPGGuild
                            .getPlayersGuild(args[2]);
                    if (g != null) {
                        Set<String> names = g.gMember.keySet();
                        mPlayer.sendMessage(ChatColor.GREEN + g.gName + ":");
                        for (String s : names) {
                            mPlayer.sendMessage(AncientRPGGuildRanks
                                    .getChatColorByRank(g.gMember.get(s)) + s);
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                + "This player does not exist, please use the exact name.");
                    }
                }
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't a guild admin.");
        }
    }
}