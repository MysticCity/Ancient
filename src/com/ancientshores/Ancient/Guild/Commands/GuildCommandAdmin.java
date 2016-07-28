package com.ancientshores.Ancient.Guild.Commands;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import com.ancientshores.Ancient.Guild.GuildBrand;

public class GuildCommandAdmin {
    @SuppressWarnings("deprecation")
	public static void processAdmin(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (mPlayer.hasPermission(AncientGuild.gNodeAdmin)) {
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("save")) {
                    AncientGuild.writeGuilds();
                }

            } else if (args.length == 3) {
                if (args[1].equals("db")) {
                    AncientGuild g = AncientGuild.getPlayersGuild(Bukkit.getPlayer(args[2]).getUniqueId());
                    if (g != null) {
                        g.disband(true);
                    } else {
                        mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "This player does not exist, please use the exact name.");
                    }
                } else if (args[1].equals("list")) {
                    AncientGuild g = AncientGuild.getPlayersGuild(Bukkit.getPlayer(args[2]).getUniqueId());
                    if (g != null) {
                        Set<UUID> uuids = g.gMember.keySet();
                        mPlayer.sendMessage(ChatColor.GREEN + g.guildName + ":");
                        for (UUID uuid : uuids) {
                            mPlayer.sendMessage(AncientGuildRanks.getChatColorByRank(g.gMember.get(uuid)) + PlayerFinder.getPlayerName(uuid));
                        }
                    } else {
                        mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "This player does not exist, please use the exact name.");
                    }
                }
            }
        } else {
            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You aren't a guild admin.");
        }
    }
}