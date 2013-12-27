package com.ancientshores.AncientRPG.Party.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandInvite {
    public static void processInvite(CommandSender sender, String[] args,
                                     AncientRPG main) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            if (AncientRPG.hasPermissions(mPlayer, AncientRPGParty.pNodeCreate)) {
                if (AncientRPGParty.getPlayersParty(mPlayer) == null) {
                    Player invitedPlayer = main.getServer().getPlayer(args[1]);
                    if (invitedPlayer != null && invitedPlayer != mPlayer) {
                        if (AncientRPG.hasPermissions(invitedPlayer,
                                AncientRPGParty.pNodeJoin)) {
                            if (AncientRPGParty.getPlayersParty(invitedPlayer) == null) {
                                if (!AncientRPGParty.invites
                                        .containsKey(invitedPlayer)) {
                                    if (!AncientRPGParty.invites
                                            .containsKey(mPlayer)) {
                                        if (!AncientRPGParty.mIgnoreList
                                                .contains(invitedPlayer)) {
                                            final AncientRPGParty mParty = new AncientRPGParty(
                                                    mPlayer);
                                            AncientRPGParty.partys.add(mParty);
                                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                                    + "Succesfully invited "
                                                    + ChatColor.GOLD
                                                    + main.getServer()
                                                    .getPlayer(args[1])
                                                    .getName()
                                                    + ChatColor.BLUE
                                                    + " to your new party.");
                                            invitedPlayer
                                                    .sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                                            + "You were invited to a party by "
                                                            + ChatColor.GOLD
                                                            + mPlayer.getName()
                                                            + ChatColor.BLUE
                                                            + ".");
                                            invitedPlayer
                                                    .sendMessage(ChatColor.BLUE
                                                            + "Use /party accept or /party reject to join the party or reject the invitation.");
                                            invitedPlayer
                                                    .sendMessage(ChatColor.BLUE
                                                            + "If you want to ignore the invites of all players use /party ignore.");
                                            if (AncientRPGParty.invites
                                                    .containsKey(invitedPlayer)) {
                                                AncientRPGParty.invites
                                                        .remove(invitedPlayer);
                                            }
                                            AncientRPGParty.invites.put(
                                                    invitedPlayer, mParty);
                                        } else {
                                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                                    + "This player ignores all invitations.");
                                        }
                                    } else {
                                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                                + "You are already invited to a party, please reject (/party reject) it first.");
                                    }
                                } else {
                                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                            + "This player already is invited. He has to reject (/party reject) it first.");
                                }
                            } else {
                                sender.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "This player is already in a party.");
                            }
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                    + "This player doesn't have the permissions to join a party.");
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                + "This player doesn't exist");
                    }
                } else {
                    Player invitedPlayer = main.getServer().getPlayer(args[1]);
                    AncientRPGParty mParty = AncientRPGParty
                            .getPlayersParty(mPlayer);
                    if (mParty.getMemberNumber() < AncientRPGParty.maxPlayers) {
                        if (invitedPlayer != null && invitedPlayer != mPlayer) {
                            if (AncientRPG.permissionHandler == null
                                    || AncientRPG.permissionHandler.has(
                                    invitedPlayer,
                                    AncientRPGParty.pNodeJoin)) {
                                if (AncientRPGParty
                                        .getPlayersParty(invitedPlayer) == null) {
                                    if (!AncientRPGParty.invites
                                            .containsKey(invitedPlayer)) {
                                        if (!AncientRPGParty.invites
                                                .containsKey(mPlayer)) {
                                            if (mPlayer == mParty.mLeader) {
                                                if (!AncientRPGParty.mIgnoreList
                                                        .contains(invitedPlayer)) {
                                                    mParty.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                                            + "Player "
                                                            + ChatColor.GOLD
                                                            + main.getServer()
                                                            .getPlayer(
                                                                    args[1])
                                                            .getName()
                                                            + ChatColor.BLUE
                                                            + " was invited to your party.");
                                                    if (AncientRPGParty.invites
                                                            .containsKey(invitedPlayer)) {
                                                        AncientRPGParty.invites
                                                                .remove(invitedPlayer);
                                                    }
                                                    AncientRPGParty.invites
                                                            .put(invitedPlayer,
                                                                    mParty);
                                                    invitedPlayer
                                                            .sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                                                    + "You were invited to a party by "
                                                                    + ChatColor.GOLD
                                                                    + mPlayer
                                                                    .getName()
                                                                    + ChatColor.BLUE
                                                                    + ".");
                                                    invitedPlayer
                                                            .sendMessage(ChatColor.BLUE
                                                                    + "Use /paccept to join the Party or /party reject to reject the invitation.");
                                                    invitedPlayer
                                                            .sendMessage(ChatColor.BLUE
                                                                    + "If you want to ignore the invites of all players use /party ignore.");
                                                } else {
                                                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                                            + "This player is ignoring all party invitations.");
                                                }
                                            } else {
                                                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                                        + "You are not the leader of the party.");
                                            }
                                        } else {
                                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                                    + "You are already invited to a party, please reject (/party reject) it first.");
                                        }
                                    } else {
                                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                                + "This player already is invited. He has to reject (/party reject) it first.");
                                    }
                                } else {
                                    sender
                                            .sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "This player is already in a party.");
                                }
                            } else {
                                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                        + "This player hasn't the required permissions to join a party.");
                            }
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                    + "This player doesn't exist");
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                + "Your party is already full.");
                    }
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                        + "You don't have the permissions to create a party.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                    + "Correct usage: /party invite <name>");
        }
    }
}
