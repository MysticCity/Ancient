package com.ancientshores.AncientRPG.Guild.Commands;

import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancient.util.PlayerFinder;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;

public class GuildCommandMemberlist {
    public static void processMemberList(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientRPGGuild g = AncientRPGGuild.getPlayersGuild(mPlayer.getUniqueId());
        if (g != null) {
            Set<UUID> uuids = g.gMember.keySet();
            mPlayer.sendMessage(ChatColor.GREEN + g.guildName + ":");
            for (UUID uuid : uuids) {
                mPlayer.sendMessage(AncientRPGGuildRanks.getChatColorByRank(g.gMember.get(uuid)) + PlayerFinder.getPlayerName(uuid));
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
        }
    }
}