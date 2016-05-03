package com.ancientshores.Ancient.Party.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Language.Prefix;
import com.ancientshores.Ancient.Party.AncientParty;

public class PartyCommandKick {
    
    protected static String PartyBrand = Prefix.get( Ancient.systemLang.getText("Party.PartyBrand") );
    
    @SuppressWarnings("deprecation")
	public static void processKick(CommandSender sender, String[] args, Ancient main) {
        Player mPlayer = (Player) sender;
        if (args.length == 2) {
            AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
            if (mParty != null) {
                if (mParty.getLeader().compareTo(mPlayer.getUniqueId()) == 0) {
                    Player mKickedPlayer = main.getServer().getPlayer(args[1]);
                    mParty.removePlayer(mKickedPlayer.getUniqueId());
                    mParty.sendMessage(PartyBrand + ChatColor.GOLD + mKickedPlayer.getName() + ChatColor.BLUE + " was kicked out of your party.");
                    mKickedPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You were kicked out of the party.");
                } else {
                    mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't the leader of the party.");
                }
            } else {
                mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "You aren't in a party.");
            }
        } else {
            mPlayer.sendMessage(PartyBrand + ChatColor.BLUE + "Correct usage: /pkick <name>");
        }
    }
}