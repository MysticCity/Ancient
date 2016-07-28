package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import com.ancientshores.Ancient.Guild.GuildBrand;

public class GuildCommandSetTag {
    public static void processSetTag(CommandSender sender, String[] args) {
        if (!AncientGuild.tagenabled) {
            return;
        }
        Player mPlayer = (Player) sender;
        if (args.length >= 2) {
            AncientGuild mGuild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
            if (mGuild != null) {
                if (AncientGuildRanks.hasMotdRights(mGuild.gMember.get(mPlayer.getUniqueId()))) {
                    args[0] = "";
                    mGuild.setTag(Ancient.convertStringArrayToString(args).trim());
                    AncientGuild.writeGuilds();
                    mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.GREEN + "Guild tag successfully set!");
                }
            } else {
                mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "You are in no guild.");
            }
        } else {
            mPlayer.sendMessage(GuildBrand.getDefaultGuildBrand() + ChatColor.RED + "Correct usage: /guild settag <tag>");
        }
    }
}