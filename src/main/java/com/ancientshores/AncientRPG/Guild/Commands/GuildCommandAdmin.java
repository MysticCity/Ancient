package com.ancientshores.AncientRPG.Guild.Commands;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import com.ancientshores.AncientRPG.Util.AncientRPGPlayers;

public class GuildCommandAdmin {
    @SuppressWarnings("deprecation")
	public static void processAdmin(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (mPlayer.hasPermission(AncientRPGGuild.gNodeAdmin)) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("save")) {
                    AncientRPGGuild.writeGuilds();
                }

            } else if (args.length == 3) {
                if (args[1].equals("db")) {
                    AncientRPGGuild g = AncientRPGGuild.getPlayersGuild(Bukkit.getPlayer(args[2]).getUniqueId());
                    if (g != null) {
                        g.disband(true);
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "This player does not exist, please use the exact name.");
                    }
                } else if (args[1].equals("list")) {
                    AncientRPGGuild g = AncientRPGGuild.getPlayersGuild(Bukkit.getPlayer(args[2]).getUniqueId());
                    if (g != null) {
                        Set<UUID> uuids = g.gMember.keySet();
                        mPlayer.sendMessage(ChatColor.GREEN + g.guildName + ":");
                        for (UUID uuid : uuids) {
                            mPlayer.sendMessage(AncientRPGGuildRanks.getChatColorByRank(g.gMember.get(uuid)) + AncientRPGPlayers.getPlayerName(uuid));
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "This player does not exist, please use the exact name.");
                    }
                }
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't a guild admin.");
        }
    }
}