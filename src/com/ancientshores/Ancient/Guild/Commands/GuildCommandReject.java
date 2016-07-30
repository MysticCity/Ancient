package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.GuildBrand;

public class GuildCommandReject {
    public static void processReject(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientGuild.invites.containsKey(mPlayer.getUniqueId())) {
            AncientGuild.invites.remove(mPlayer.getUniqueId());
            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.GREEN + "Succesfully rejected the guild invitation.");
        } else {
            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You weren't invited to a guild.");
        }
    }
}