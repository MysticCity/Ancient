package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandInvite {
    public static void processInvite(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer
                    .getName());
            if (mGuild != null) {
                if (AncientRPGGuildRanks.hasInviteRights(mGuild.gMember
                        .get(mPlayer.getName()))) {
                    Player mInvitedPlayer = AncientRPG.plugin.getServer()
                            .getPlayer(args[1]);
                    if (AncientRPGGuild.getPlayersGuild(mInvitedPlayer
                            .getName()) == null
                            && mInvitedPlayer != null
                            && !AncientRPGGuild.invites
                            .containsKey(mInvitedPlayer)) {
                        if (AncientRPG.hasPermissions(mInvitedPlayer,
                                AncientRPGGuild.gNodeJoin)) {
                            if (AncientRPGGuild.invites.size() < AncientRPGGuild.maxPlayers) {
                                AncientRPGGuild.invites.put(mInvitedPlayer,
                                        mGuild);
                                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
                                        + "Invited " + mInvitedPlayer.getName()
                                        + " to your guild.");
                                mInvitedPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
                                        + "You were invited to the guild "
                                        + "\"" + mGuild.gName + "\"" + " by "
                                        + mPlayer.getName() + ".");
                                mInvitedPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
                                        + "Use /guild accept or/guild reject.");
                            } else {
                                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                        + "Your guild is already full.");
                            }
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                    + "This Player hasn't the permission to join a guild.");
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                + "This Player already has a guild or doesn't exist or already is invited.");
                    }
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                            + "You don't have the rank to invite people.");
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are in no guild.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                    + "Correct usage: /guild invite <player>");
        }
    }
}
