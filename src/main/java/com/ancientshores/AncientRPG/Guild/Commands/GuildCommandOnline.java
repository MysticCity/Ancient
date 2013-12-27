package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class GuildCommandOnline {
    public static void processOnline(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(mPlayer
                .getName());
        if (guild != null) {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
                    + "Guild members online:");
            Set<String> guildplayers = guild.gMember.keySet();
            for (String playersg : guildplayers) {
                if (AncientRPG.plugin.getServer().getPlayer(playersg) != null
                        && AncientRPG.plugin.getServer().getPlayer(playersg)
                        .isOnline()) {
                    mPlayer.sendMessage(AncientRPGGuildRanks
                            .getChatColorByRank(guild.gMember.get(playersg))
                            + playersg);
                }
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
        }
    }
}
