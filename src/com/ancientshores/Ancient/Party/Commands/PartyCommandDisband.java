package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Language.Prefix;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandDisband {
    
    protected static String PartyBrand = Prefix.get( Ancient.systemLang.getText("Party.PartyBrand") );
    
    public static void processDisband(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
        if (mParty != null) {
            if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0) {
                mParty.removeAll();
                AncientParty.partys.remove(mParty);
            } else {
                mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't the leader of this party.");
            }
        } else {
            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't in a party.");
        }
    }
}