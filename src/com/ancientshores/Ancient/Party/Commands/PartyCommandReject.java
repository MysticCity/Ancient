package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Language.Prefix;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandReject {
    
    protected static String PartyBrand = Prefix.get( Ancient.systemLang.getText("Party.PartyBrand") );
    
    public static void processReject(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (AncientParty.invites.containsKey(mPlayer.getUniqueId())) {
            AncientParty.invites.get(mPlayer.getUniqueId()).sendMessage(PartyBrand + ChatColor.GOLD + mPlayer.getName() + ChatColor.BLUE + " rejected your invite.");
            AncientParty.invites.remove(mPlayer.getUniqueId());
        } else {
            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You weren't invited in a party.");
        }
    }
}