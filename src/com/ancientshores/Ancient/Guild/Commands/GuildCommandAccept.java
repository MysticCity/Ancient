package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import com.ancientshores.Ancient.Guild.GuildBrand;

public class GuildCommandAccept {
    public static void processAccept(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientGuild.getPlayersGuild(mPlayer.getUniqueId()) == null) {
            if (AncientGuild.invites.containsKey(mPlayer.getUniqueId())) {
                if (AncientGuild.guilds.contains(AncientGuild.invites.get(mPlayer.getUniqueId()))) {
                    if (AncientGuild.invites.size() < AncientGuild.maxPlayers) {
                        AncientGuild.invites.get(mPlayer.getUniqueId()).broadcastMessage(GuildBrand.getDefaultGuildBrand() +
                                ChatColor.AQUA + mPlayer.getName() + ChatColor.GREEN + " joined your guild.");
                        AncientGuild.invites.get(mPlayer.getUniqueId()).addMember(mPlayer.getUniqueId(), AncientGuildRanks.TRIAL);
                        mPlayer.sendMessage(ChatColor.GREEN + "Succesfully joined the guild.");
                        AncientGuild.writeGuild(AncientGuild.invites.get(mPlayer.getUniqueId()));
                        AncientGuild.invites.remove(mPlayer.getUniqueId());
                    } else {
                        mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "This guild is already full.");
                        AncientGuild.invites.remove(mPlayer.getUniqueId());
                    }
                } else {
                    mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "This guild doesn't exist anymore.");
                    AncientGuild.invites.remove(mPlayer.getUniqueId());
                }
            } else {
                mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You weren't invited to a guild.");
            }
        } else {
            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You are already in a guild.");
        }
    }
}