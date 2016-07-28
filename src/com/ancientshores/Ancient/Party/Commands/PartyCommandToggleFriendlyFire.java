package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Language.Prefix;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandToggleFriendlyFire {
    
    protected static String PartyBrand = Prefix.get( Ancient.systemLang.getText("Party.PartyBrand") );
    
    public static void processFF(CommandSender sender) {
        Player mPlayer = (Player) sender;
        AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
        if (AncientGuild.canToggleff) {
            if (mParty != null) {
                if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0) {
                    mParty.setFriendlyFireEnabled(!mParty.isFriendlyFireEnabled());
                    mParty.sendMessage(PartyBrand + ChatColor.BLUE + "Friendly fire is now: " + ChatColor.GREEN + (mParty.isFriendlyFireEnabled() ? "off" : "on"));
                } else {
                    mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You don't have permission to toggle friendly fire");
                }
            } else {
                mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't in a party!");
            }
        } else {
            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "Config disallows toggling ff of parties!");
        }
    }
}