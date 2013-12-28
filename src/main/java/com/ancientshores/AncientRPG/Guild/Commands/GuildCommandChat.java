package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandChat {
    public static void processChat(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length >= 2) {
            AncientRPGGuild mGuild = AncientRPGGuild.getPlayersGuild(mPlayer
                    .getName());
            if (mGuild != null) {
                args[0] = "";
                mGuild.sendMessage(AncientRPG.convertStringArrayToString(args),
                        mPlayer);
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED + "You are in no guild.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                    + "Correct usage: /guild chat <message> or /gs <message> or /gc <message>");
        }
    }
}