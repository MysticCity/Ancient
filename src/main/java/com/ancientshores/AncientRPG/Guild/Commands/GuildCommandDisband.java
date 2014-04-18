package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandDisband {
    public static void processDisband(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer.getName());
        if (mGuild != null) {
            if (mGuild.gLeader.equals(mPlayer.getName())) {
                mGuild.disband(false);
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "Only the leader can disband the guild.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
        }
    }
}