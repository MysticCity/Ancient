package com.ancientshores.Ancient.Mana;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;

public class ManaCommand {
    public static void processManaCommand(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
        	PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
            p.sendMessage(Ancient.ChatBrand + "Your mana regeneration per " + pd.getManasystem().manareginterval + " seconds is " + pd.getManasystem().manareg);
            p.sendMessage(Ancient.ChatBrand + "You currently have " + pd.getManasystem().curmana + " mana, your max mana is " + pd.getManasystem().maxmana);
        }
    }
}