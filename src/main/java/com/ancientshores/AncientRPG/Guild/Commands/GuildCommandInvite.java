package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;

public class GuildCommandInvite {
    @SuppressWarnings("deprecation")
	public static void processInvite(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 2) {
            AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(player.getUniqueId());
            if (guild != null) {
                if (AncientRPGGuildRanks.hasInviteRights(guild.gMember.get(player.getUniqueId()))) {
                    Player invitedPlayer = Bukkit.getPlayer(args[1]);
                    if (invitedPlayer != null && AncientRPGGuild.getPlayersGuild(invitedPlayer.getUniqueId()) == null && !AncientRPGGuild.invites.containsKey(invitedPlayer.getUniqueId())) {
                        if (invitedPlayer.hasPermission(AncientRPGGuild.gNodeJoin)) {
                            if (AncientRPGGuild.invites.size() < AncientRPGGuild.maxPlayers) {
                                AncientRPGGuild.invites.put(invitedPlayer.getUniqueId(), guild);
                                player.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "Invited " + invitedPlayer.getName() + " to your guild.");
                                invitedPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "You were invited to the guild " + "\"" + guild.guildName + "\"" + " by " + player.getName() + ".");
                                invitedPlayer.sendMessage(AncientRPG.brand2 + ChatColor.GREEN + "Use /guild accept or/guild reject.");
                            } else {
                                player.sendMessage(AncientRPG.brand2 + ChatColor.RED + "Your guild is already full.");
                            }
                        } else {
                            player.sendMessage(AncientRPG.brand2 + ChatColor.RED + "This Player hasn't the permission to join a guild.");
                        }
                    } else {
                        player.sendMessage(AncientRPG.brand2 + ChatColor.RED + "This Player already has a guild or doesn't exist or already is invited.");
                    }
                } else {
                    player.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You don't have the rank to invite people.");
                }
            } else {
                player.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are in no guild.");
            }
        } else {
            player.sendMessage(AncientRPG.brand2 + ChatColor.RED + "Correct usage: /guild invite <player>");
        }
    }
}