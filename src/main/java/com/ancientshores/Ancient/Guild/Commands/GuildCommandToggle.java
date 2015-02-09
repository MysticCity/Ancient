package com.ancientshores.Ancient.Guild.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Listeners.AncientPlayerListener;

public class GuildCommandToggle {
    public static void processToggle(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (!AncientPlayerListener.toggleguildlist.contains(mPlayer.getUniqueId())) {
            AncientPlayerListener.toggleguildlist.add(mPlayer.getUniqueId());
            mPlayer.sendMessage(Ancient.brand2 + "Guild chat activated");
        } else {
            AncientPlayerListener.toggleguildlist.remove(mPlayer.getUniqueId());
            mPlayer.sendMessage(Ancient.brand2 + "Guild chat deactivated");
        }
    }
}