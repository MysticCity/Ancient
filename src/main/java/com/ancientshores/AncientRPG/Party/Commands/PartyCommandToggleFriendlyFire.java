package com.ancientshores.AncientRPG.Party.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandToggleFriendlyFire {
    public static void processFF(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
        if (AncientRPGGuild.canToggleff) {
            if (mParty != null) {
                if (mParty.mLeader.equals(mPlayer)) {
                    if (mParty.friendlyFire) {
                        mParty.friendlyFire = false;
                        mParty.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                + "Friendly fire is now: " + ChatColor.GREEN
                                + "off");
                    } else {
                        mParty.friendlyFire = true;
                        mParty.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                + "Friendly fire is now: " + ChatColor.RED
                                + "on");
                    }
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                            + "You don't have permission to toggle friendly fire");
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't in a party!");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "Config disallows toggling ff of parties!");
        }
    }
}
