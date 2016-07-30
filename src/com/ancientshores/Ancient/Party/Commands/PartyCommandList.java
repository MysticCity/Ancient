package com.ancientshores.Ancient.Party.Commands;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Language.Prefix;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandList {
    
    protected static String PartyBrand = Prefix.get( Ancient.systemLang.getText("Party.PartyBrand") );
    
    public static void processList(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientParty.getPlayersParty(mPlayer.getUniqueId()) != null) {
            if (mPlayer.hasPermission("")) { // ??? --- mega sinnlos
                AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
                mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "In your party are:");
                for (UUID uuid : mParty.getMembers()) {
                    if (uuid != null) {
                        if (uuid == AncientParty.getPlayersParty(mPlayer.getUniqueId()).getLeader()) {
                            mPlayer.sendMessage(ChatColor.GREEN + PlayerFinder.getPlayerName(uuid) + "(Leader)");
                        } else {
                            mPlayer.sendMessage(ChatColor.GOLD + PlayerFinder.getPlayerName(uuid));
                        }
                    }
                }
            } else {
                mPlayer.sendMessage(PartyBrand + ChatColor.RED + "You don't have the permissions to list the party members.");
            }
        } else {
            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't in a party.");
        }
    }
}