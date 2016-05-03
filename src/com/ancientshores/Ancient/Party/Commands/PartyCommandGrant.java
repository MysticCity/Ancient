package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Language.Prefix;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandGrant {
    
    protected static String PartyBrand = Prefix.get( Ancient.systemLang.getText("Party.PartyBrand") );
    
    @SuppressWarnings("deprecation")
	public static void processGrant(CommandSender sender, String[] args, Ancient main) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
            if (mParty != null) {
                if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (Bukkit.getPlayer(args[1]).hasPermission(AncientParty.pNodeCreate)) {
                            mParty.setLeader(Bukkit.getPlayer(args[1]).getUniqueId());
                            mParty.sendMessage(PartyBrand + ChatColor.GREEN + PlayerFinder.getPlayerName(mParty.getLeader()) + ChatColor.BLUE + " is the new leader of the party.");
                            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "Succesfully granted " + ChatColor.GOLD + PlayerFinder.getPlayerName(mParty.getLeader()) + ChatColor.BLUE + " leader rights.");
                        } else {
                            mPlayer.sendMessage(PartyBrand + ChatColor.RED + "The player you chose doesn't have the permissions to lead a party.");
                        }
                    } else {
                        mPlayer.sendMessage(PartyBrand + ChatColor.RED + "The player you invited doesn't exist.");
                    }
                } else {
                    mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't the leader of this party.");
                }
            } else {
                mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't in a party.");
            }
        } else {
            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "Correct usage: /pgrant <name>");
        }
    }
}