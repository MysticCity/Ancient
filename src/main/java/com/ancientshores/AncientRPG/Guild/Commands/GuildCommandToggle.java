package com.ancientshores.AncientRPG.Guild.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Listeners.AncientRPGPlayerListener;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommandToggle {
    public static void processToggle(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (!AncientRPGPlayerListener.toggleguildlist.contains(mPlayer)) {
            AncientRPGPlayerListener.toggleguildlist.add(mPlayer);
            mPlayer.sendMessage(AncientRPG.brand2 + "Guild chat activated");
        } else {
            AncientRPGPlayerListener.toggleguildlist.remove(mPlayer);
            mPlayer.sendMessage(AncientRPG.brand2 + "Guild chat deactivated");
        }
    }
}
