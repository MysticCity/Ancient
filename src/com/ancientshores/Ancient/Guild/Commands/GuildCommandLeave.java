package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;

public class GuildCommandLeave {
    public static void processLeave(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientGuild guild = AncientGuild.getPlayersGuild(mPlayer.getUniqueId());
        if (guild != null) {
            ChatColor farbe = AncientGuildRanks.getChatColorByRank(guild.getGuildMembers().get(mPlayer.getUniqueId()));
            guild.gMember.remove(mPlayer.getUniqueId());
            AncientGuild.writeGuild(guild);
            guild.broadcastMessage(Ancient.brand2 + farbe + mPlayer.getName() + ChatColor.GREEN + " left the guild.");
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Succesfully left your guild.");
            mPlayer.setDisplayName(mPlayer.getName());
            if (mPlayer.getUniqueId().compareTo(guild.gLeader) == 0 && guild.gMember.size() > 0) {
                mPlayer.sendMessage(Ancient.brand2 + "You can't leave the guild as a leader unless you are the only one in the guild.");
            }
        } else {
            mPlayer.sendMessage(Ancient.brand2 + ChatColor.RED + "You aren't in a guild.");
        }
    }
}