package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Language.Prefix;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandLeave {
    
    protected static String PartyBrand = Prefix.get( Ancient.systemLang.getText("Party.PartyBrand") );
    
    public static void processLeave(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientParty.getPlayersParty(mPlayer.getUniqueId()) != null) {
            AncientParty mPlayersParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
            mPlayersParty.removePlayer(mPlayer.getUniqueId());
            mPlayersParty.giveNextLeader();
            mPlayersParty.sendMessage(PartyBrand + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + " left your party.");
            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "Successfully left the party.");
            if (mPlayersParty.getMemberNumber() == 1) {
                mPlayersParty.sendMessage(PartyBrand + ChatColor.RED + "Your party has been deleted.");
                AncientParty.partys.remove(mPlayersParty);
            }
        } else {
            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't in a party.");
        }
    }
}