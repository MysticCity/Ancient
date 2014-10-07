package com.ancientshores.AncientRPG.Guild.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;

public class GuildCommandSetTag {
    public static void processSetTag(CommandSender sender, String[] args) {
        if (!AncientRPGGuild.tagenabled) {
            return;
        }
        Player mPlayer = (Player) sender;
        if (args.length >= 2) {
            AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer.getUniqueId());
            if (mGuild != null) {
                if (AncientRPGGuildRanks.hasMotdRights(mGuild.gMember.get(mPlayer.getUniqueId()))) {
                    args[0] = "";
                    mGuild.tag = AncientRPG.convertStringArrayToString(args).trim();
                    for (UUID uuid : mGuild.gMember.keySet()) {
                        Player p = Bukkit.getPlayer(uuid);
                        if (p != null) {
                            AncientRPGGuild.setTag(p);
                        }
                    }
                    AncientRPGGuild.writeGuilds();
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are in no guild.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "Correct usage: /guild settag <tag>");
        }
    }
}