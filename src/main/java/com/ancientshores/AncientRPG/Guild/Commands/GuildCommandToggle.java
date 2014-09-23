package com.ancientshores.AncientRPG.Guild.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Listeners.AncientRPGPlayerListener;

public class GuildCommandToggle {
    public static void processToggle(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (!AncientRPGPlayerListener.toggleguildlist.contains(mPlayer.getUniqueId())) {
            AncientRPGPlayerListener.toggleguildlist.add(mPlayer.getUniqueId());
            mPlayer.sendMessage(AncientRPG.brand2 + "Guild chat activated");
        } else {
            AncientRPGPlayerListener.toggleguildlist.remove(mPlayer);
            mPlayer.sendMessage(AncientRPG.brand2 + "Guild chat deactivated");
        }
    }
}