package com.ancientshores.AncientRPG.Party.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandDisband {
    public static void processDisband(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
        if (mParty != null) {
            if (mParty.mLeader == mPlayer) {
                mParty.removeAll();
                AncientRPGParty.partys.remove(mParty);
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                        + "You aren't the leader of this party.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't in a party.");
        }
    }
}
