package com.ancientshores.AncientRPG.Party.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandGrant {
    public static void processGrant(CommandSender sender, String[] args,
                                    AncientRPG main) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            AncientRPGParty mParty = AncientRPGParty.getPlayersParty(mPlayer);
            if (mParty != null) {
                if (mParty.mLeader == mPlayer) {
                    if (main.getServer().getPlayer(args[1]) != null) {
                        if (AncientRPG.hasPermissions(main.getServer()
                                .getPlayer(args[1]), AncientRPGParty.pNodeCreate)) {
                            mParty.mLeader = main.getServer().getPlayer(
                                    args[1]);
                            mParty.sendMessage(AncientRPG.brand2 + ChatColor.GREEN
                                    + mParty.mLeader.getName()
                                    + ChatColor.BLUE
                                    + " is the new leader of the party.");
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                                    + "Succesfully granted "
                                    + ChatColor.GOLD
                                    + mParty.mLeader.getName()
                                    + ChatColor.BLUE + " leader rights.");
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                    + "The player you chose doesn't have the permissions to lead a party.");
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.RED
                                + "The player you invited doesn't exist.");
                    }
                } else {
                    mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                            + "You aren't the leader of this party.");
                }
            } else {
                mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                        + "You aren't in a party.");
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE
                    + "Correct usage: /pgrant <name>");
        }
    }
}