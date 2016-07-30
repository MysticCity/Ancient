package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import com.ancientshores.Ancient.Guild.GuildBrand;

public class GuildCommandSetMotd {
    public static void processSetMOTD(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length >= 2) {
            AncientGuild guild = AncientGuild.getPlayersGuild(player.getUniqueId());
            if (guild != null) {
                if (AncientGuildRanks.hasMotdRights(guild.gMember.get(player.getUniqueId()))) {
                    args[0] = "";
                    guild.motd = Ancient.convertStringArrayToString(args);
                    guild.broadcastMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.GREEN + "<Guild> motd: " + guild.motd);
                    AncientGuild.writeGuilds();
                } else {
                    player.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You do not have the rights to change the MOTD.");
                }
            } else {
                player.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You aren't in a guild.");
            }
        } else {
            player.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "Correct usage: /guild setmotd <message>");
        }
    }
}