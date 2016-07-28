package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import com.ancientshores.Ancient.Guild.GuildBrand;
import com.ancientshores.Ancient.Interactive.InteractiveMessage;

public class GuildCommandInvite {
    @SuppressWarnings("deprecation")
	public static void processInvite(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 2) {
            AncientGuild guild = AncientGuild.getPlayersGuild(player.getUniqueId());
            if (guild != null) {
                if (AncientGuildRanks.hasInviteRights(guild.gMember.get(player.getUniqueId()))) {
                    Player invitedPlayer = Bukkit.getPlayer(args[1]);
                    if (invitedPlayer != null && AncientGuild.getPlayersGuild(invitedPlayer.getUniqueId()) == null && !AncientGuild.invites.containsKey(invitedPlayer.getUniqueId())) {
                        if (invitedPlayer.hasPermission(AncientGuild.gNodeJoin)) {
                            if (AncientGuild.invites.size() < AncientGuild.maxPlayers) {
                                AncientGuild.invites.put(invitedPlayer.getUniqueId(), guild);
                                player.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.GREEN + "Invited " + invitedPlayer.getName() + " to your guild.");
                                invitedPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.GREEN + "You were invited to the guild " + "\"" + guild.guildName + "\"" + " by " + player.getName() + ".");
                                
                                //Send accept-request
                                InteractiveMessage interactiveAccept = new InteractiveMessage("&2► Accept invite","guild accept");
                                interactiveAccept.sendToPlayer(invitedPlayer);
                                
                                //Send reject-request
                                InteractiveMessage interactiveReject = new InteractiveMessage("&c► Reject invite","guild reject");
                                interactiveReject.sendToPlayer(invitedPlayer);
                                
                            } else {
                                player.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "Your guild is already full.");
                            }
                        } else {
                            player.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "This Player hasn't the permission to join a guild.");
                        }
                    } else {
                        player.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "This Player already has a guild or doesn't exist or already is invited.");
                    }
                } else {
                    player.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You don't have the rank to invite people.");
                }
            } else {
                player.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You are in no guild.");
            }
        } else {
            player.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "Correct usage: /guild invite <player>");
        }
    }
}