package com.ancientshores.AncientRPG.Party.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandChat {
    public static void processChat(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
        if (mParty == null) {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't in a party.");
        } else {
            String[] message = new String[args.length - 1];
            System.arraycopy(args, 1, message, 0, args.length - 1);
            mParty.sendMessage(message, mPlayer);
        }
    }
}