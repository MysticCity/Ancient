package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandMotd {
    public static void processMotd(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer.getName());
        if (mGuild != null) {
            mPlayer.sendMessage(ChatColor.GREEN + "<Guild> motd: "
                    + mGuild.motd);
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You aren't in a guild.");
        }
    }
}
