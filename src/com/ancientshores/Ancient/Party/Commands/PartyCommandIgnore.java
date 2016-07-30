package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Language.Prefix;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandIgnore {
    
    protected static String PartyBrand = Prefix.get( Ancient.systemLang.getText("Party.PartyBrand") );
    
    public static void processIgnore(CommandSender sender, String[] args) {
        Player mPlayer = (Player) sender;
        if (args.length == 0) {
            if (!AncientParty.mIgnoreList.contains(mPlayer.getUniqueId())) {
                AncientParty.mIgnoreList.add(mPlayer.getUniqueId());
                mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You are ignoring all invitations now.");
            } else {
                AncientParty.mIgnoreList.remove(mPlayer.getUniqueId());
                mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't ignoring invitations anymore.");
            }
        } else {
            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "Correct usage: /pignore");
        }
    }
}