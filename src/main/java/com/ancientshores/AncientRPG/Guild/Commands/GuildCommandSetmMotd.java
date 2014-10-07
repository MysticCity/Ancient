package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;

public class GuildCommandSetmMotd {
    public static void processSetmotd(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length >= 2) {
            AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(player.getUniqueId());
            if (guild != null) {
                if (AncientRPGGuildRanks.hasMotdRights(guild.gMember.get(player.getUniqueId()))) {
                    args[0] = "";
                    guild.motd = AncientRPG.convertStringArrayToString(args);
                    guild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + "<Guild> motd: " + guild.motd);
                    AncientRPGGuild.writeGuilds();
                } else {
                    player.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You do not have the rights to change the MOTD.");
                }
            } else {
                player.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
            }
        } else {
            player.sendMessage(AncientRPG.brand2 + ChatColor.RED + "Correct usage: /guild setmotd <message>");
        }
    }
}