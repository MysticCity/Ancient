package com.ancientshores.AncientRPG.Mana;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;

public class ManaCommand {
    public static void processManaCommand(CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
        	PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
            p.sendMessage(AncientRPG.brand2 + "Your mana regeneration per " + pd.getManasystem().manareginterval + " seconds is " + pd.getManasystem().manareg);
            p.sendMessage(AncientRPG.brand2 + "You currently have " + pd.getManasystem().curmana + " mana, your max mana is " + pd.getManasystem().maxmana);
        }
    }
}