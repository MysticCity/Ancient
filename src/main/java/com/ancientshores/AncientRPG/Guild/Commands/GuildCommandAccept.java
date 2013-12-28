package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandAccept {
    public static void processAccept(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientRPGGuild.getPlayersGuild(mPlayer.getName()) == null) {
            if (AncientRPGGuild.invites.containsKey(mPlayer)) {
                if (AncientRPGGuild.guilds.contains(AncientRPGGuild.invites
                        .get(mPlayer))) {
                    if (AncientRPGGuild.invites.size() < AncientRPGGuild.maxPlayers) {
                        AncientRPGGuild.invites.get(mPlayer).broadcastMessage(AncientRPG.brand2 +
                                ChatColor.AQUA + mPlayer.getName()
                                + ChatColor.GREEN
                                + " joined your guild.");
                        AncientRPGGuild.invites.get(mPlayer).addMember(
                                mPlayer.getName(), AncientRPGGuildRanks.TRIAL);
                        mPlayer.sendMessage(ChatColor.GREEN
                                + "Succesfully joined the guild.");
                        AncientRPGGuild.writeGuild(AncientRPGGuild.invites.get(mPlayer));
                        AncientRPGGuild.invites.remove(mPlayer);
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                + "This guild is already full.");
                        AncientRPGGuild.invites.remove(mPlayer);
                    }
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                            + "This guild doesn't exist anymore.");
                    AncientRPGGuild.invites.remove(mPlayer);
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                        + "You weren't invited to a guild.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are already in a guild.");
        }
    }
}