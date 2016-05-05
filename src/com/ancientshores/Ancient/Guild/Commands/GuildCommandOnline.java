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

public class GuildCommandOnline {
    public static void processOnline(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientGuild guild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
        if (guild != null) {
            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.GREEN + "Guild members online:");
            Set<UUID> uuids = guild.gMember.keySet();
            for (UUID uuid : uuids) {
                if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
                    mPlayer.sendMessage(AncientGuildRanks.getChatColorByRank(guild.gMember.get(uuid)) + PlayerFinder.getPlayerName(uuid));
                }
            }
        } else {
            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You aren't in a guild.");
        }
    }
}