package com.ancientshores.AncientRPG.Party.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancient.util.PlayerFinder;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class PartyCommandAdmin {
    @SuppressWarnings("deprecation")
	public static void processAdmin(CommandSender sender, String[] args, AncientRPG main) {
        Player mPlayer = (Player) sender;
        if (mPlayer.hasPermission(AncientRPGParty.pNodeAdmin)) {
            if (args.length == 2) {
                if (args[1].equals("show")) {
                    int i = 1;
                    for (AncientRPGParty p : AncientRPGParty.partys) {
                        mPlayer.sendMessage(ChatColor.BLUE + "" + i + ": " + ChatColor.GREEN + PlayerFinder.getPlayerName(p.getLeader()));
                        i++;
                    }
                }
            }
            if (args.length == 3) {
                if (args[1].equals("pl")) {
                    Player playertolist = Bukkit.getPlayer(args[2]);
                    if (playertolist != null) {
                        if (AncientRPGParty.getPlayersParty(playertolist.getUniqueId()) != null) {
                            AncientRPGParty mParty = AncientRPGParty.getPlayersParty(playertolist.getUniqueId());
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "In this party are:");
                            for (UUID uuid : mParty.getMembers()) {
                                if (uuid != null) {
                                    if (uuid.compareTo(AncientRPGParty.getPlayersParty(mPlayer.getUniqueId()).getLeader()) == 0) {
                                        mPlayer.sendMessage(ChatColor.GREEN + PlayerFinder.getPlayerName(uuid) + "(Leader)");
                                    } else {
                                        mPlayer.sendMessage(ChatColor.GOLD + PlayerFinder.getPlayerName(uuid));
                                    }
                                }
                            }
                        } else {
                            mPlayer.sendMessage(ChatColor.BLUE + "This user isn't in a Party");
                        }
                    }
                } else if (args[1].equals("db")) {
                    Player playertolist = Bukkit.getPlayer(args[2]);
                    if (playertolist != null) {
                        if (AncientRPGParty.getPlayersParty(playertolist.getUniqueId()) != null) {
                            AncientRPGParty mParty = AncientRPGParty.getPlayersParty(playertolist.getUniqueId());
                            mParty.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "Your party has been disbandend by an admin.");
                            mParty.removeAll();
                            AncientRPGParty.partys.remove(mParty);
                        } else {
                            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "This user isn't in a Party");
                        }
                    } else {
                        mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "This player isn't in a party.");
                    }
                }
            }
        } else {
            mPlayer.sendMessage(AncientRPG.brand2 + ChatColor.BLUE + "You aren't a party admin");
        }
    }
}