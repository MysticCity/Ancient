package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuildRanks;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandSetmotd {
    public static void processSetmotd(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length >= 2) {
            AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer
                    .getName());
            if (mGuild != null) {
                if (AncientRPGGuildRanks.hasMotdRights(mGuild.gMember
                        .get(mPlayer.getName()))) {
                    args[0] = "";
                    mGuild.motd = AncientRPG.convertStringArrayToString(args);
                    mGuild.broadcastMessage(AncientRPG.brand2 + ChatColor.GREEN + "<Guild> motd: "
                            + mGuild.motd);
                    AncientRPGGuild.writeGuilds();
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                    + "Correct usage: /guild motd <message>");
        }
    }
}
