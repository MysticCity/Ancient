package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Language.Prefix;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandChat {
    
    protected static String PartyBrand = Prefix.get( Ancient.systemLang.getText("Party.PartyBrand") );
    
    public static void processChat(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
        if (mParty == null) {
            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't in a party.");
        } else {
            String[] message = new String[args.length - 1];
            System.arraycopy(args, 1, message, 0, args.length - 1);
            mParty.sendMessage(message, mPlayer);
        }
    }
}