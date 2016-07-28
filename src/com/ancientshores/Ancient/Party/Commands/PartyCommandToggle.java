package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Language.Prefix;
import com.ancientshores.Ancient.Listeners.AncientPlayerListener;

public class PartyCommandToggle {
    
    protected static String PartyBrand = Prefix.get( Ancient.systemLang.getText("Party.PartyBrand") );
    
    public static void processToggle(CommandSender sender) {
        Player mPlayer = (Player) sender;
        if (!AncientPlayerListener.togglepartylist.contains(mPlayer.getUniqueId())) {
            AncientPlayerListener.togglepartylist.add(mPlayer.getUniqueId());
            mPlayer.sendMessage(PartyBrand + "Party chat activated");
        } else {
            AncientPlayerListener.togglepartylist.remove(mPlayer.getUniqueId());
            mPlayer.sendMessage(PartyBrand + "Party chat deacticated");
        }
    }
}