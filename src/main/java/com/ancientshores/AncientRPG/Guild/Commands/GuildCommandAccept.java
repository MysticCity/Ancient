package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;

public class GuildCommandAccept {
    public static void processAccept(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientRPGGuild.getPlayersGuild(mPlayer.getUniqueId()) == null) {
            if (AncientRPGGuild.invites.containsKey(mPlayer.getUniqueId())) {
                if (AncientRPGGuild.guilds.contains(AncientRPGGuild.invites.get(mPlayer.getUniqueId()))) {
                    if (AncientRPGGuild.invites.size() < AncientRPGGuild.maxPlayers) {
                        AncientRPGGuild.invites.get(mPlayer.getUniqueId()).broadcastMessage(AncientRPG.brand2 +
                                ChatColor.AQUA + mPlayer.getName() + ChatColor.GREEN + " joined your guild.");
                        AncientRPGGuild.invites.get(mPlayer.getUniqueId()).addMember(mPlayer.getUniqueId(), AncientRPGGuildRanks.TRIAL);
                        mPlayer.sendMessage(ChatColor.GREEN + "Succesfully joined the guild.");
                        AncientRPGGuild.writeGuild(AncientRPGGuild.invites.get(mPlayer.getUniqueId()));
                        AncientRPGGuild.invites.remove(mPlayer.getUniqueId());
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "This guild is already full.");
                        AncientRPGGuild.invites.remove(mPlayer.getUniqueId());
                    }
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "This guild doesn't exist anymore.");
                    AncientRPGGuild.invites.remove(mPlayer.getUniqueId());
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You weren't invited to a guild.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are already in a guild.");
        }
    }
}