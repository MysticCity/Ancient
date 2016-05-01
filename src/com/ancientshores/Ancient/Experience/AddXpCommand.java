package com.ancientshores.Ancient.Experience;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import org.bukkit.Bukkit;

public class AddXpCommand {
    public static void addXp(CommandSender cs, String[] args) {
        
        String playername = "";
        Player target = null;
        
        if (cs instanceof Player && !cs.hasPermission(AncientExperience.nodeXPAdmin)) {
            cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "You don't have the permission to use this command");
            return;
        }
        if (args.length < 2) {
            cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "Correct usage is level setxp <amount> [player]");
            return;
        }
        int amount;
        if (args.length == 2) {
            if (!(cs instanceof Player)) {
                cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "Correct usage is level setxp amount player");
                return;
            }
            playername = cs.getName();
        }
        if (args.length == 3) {
           
            try{
               
                playername = args[2]; //Playername by argument
            
                target = Bukkit.getPlayer(playername); //Set target to argument-player
               
           } catch (Exception ex) {
               
               cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.RED + "Player not found/online !"); //If target  is offline
               return;
               
           } 
            
        }
        try {
            amount = Integer.parseInt(args[1]);
        } catch (Exception e) {
            cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "Expected Integer, recieved string");
            return;
        }
        PlayerData pd;
        
        if (args.length == 3) {
            
            pd = PlayerData.playerHasPlayerData( ((Player) target).getUniqueId() );
            
        } else {
            
            pd = PlayerData.playerHasPlayerData(((Player) cs).getUniqueId());
            
        }
        
        if (pd == null) {
            cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "Player not found");
        }
        if (pd != null) {
            pd.getXpSystem().xp += amount;
            pd.getXpSystem().addXP(0, false);
            cs.sendMessage(ChatColor.GOLD + "[" + Ancient.brand + "] " + ChatColor.YELLOW + "Successfully added " + amount + " to the experience of the player " + playername);
        }
    }
}