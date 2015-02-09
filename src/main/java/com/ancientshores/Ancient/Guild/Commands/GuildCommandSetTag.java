package com.ancientshores.Ancient.Guild.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;

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
                    mGuild.tag = Ancient.convertStringArrayToString(args).trim();
                    for (UUID uuid : mGuild.gMember.keySet()) {
                        Player p = Bukkit.getPlayer(uuid);
                        if (p != null) {
                            AncientGuild.setTag(p);
                        }
                    }
                    AncientGuild.writeGuilds();
                }
            } else {
                mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You are in no guild.");
            }
        } else {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "Correct usage: /guild settag <tag>");
        }
    }
}