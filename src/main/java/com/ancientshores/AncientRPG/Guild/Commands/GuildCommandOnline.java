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

public class GuildCommandOnline {
    public static void processOnline(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(mPlayer.getUniqueId());
        if (guild != null) {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "Guild members online:");
            Set<UUID> uuids = guild.gMember.keySet();
            for (UUID uuid : uuids) {
                if (Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).isOnline()) {
                    mPlayer.sendMessage(AncientRPGGuildRanks.getChatColorByRank(guild.gMember.get(uuid)) + AncientRPGPlayers.getPlayerName(uuid));
                }
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
        }
    }
}