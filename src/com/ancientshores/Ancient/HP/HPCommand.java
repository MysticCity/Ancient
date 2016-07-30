package com.ancientshores.Ancient.HP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;

public class HPCommand {
    public static void showHP(Player p) {
        if (DamageConverter.isEnabledInWorld(p.getWorld())) {
            PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
            double hp = p.getHealth();
            double maxhp = p.getMaxHealth();
            p.sendMessage(Ancient.ChatBrand + "You have " + getChatColorByHp(hp, maxhp) + (Math.round(hp * 100) / 100.0) + "/" + (Math.round(maxhp * 100) / 100.0) + " hp");
            p.sendMessage(Ancient.ChatBrand + "Your hp regeneration per " + pd.getHpsystem().hpRegInterval + " seconds is " + ChatColor.GREEN + pd.getHpsystem().getHPRegen());
        } else {
            double hp = p.getHealth();
            double maxhp = p.getMaxHealth();
            p.sendMessage(Ancient.ChatBrand + "You have " + getChatColorByHp(hp, maxhp) + (Math.round(hp * 100) / 100.0) + "/" + (Math.round(maxhp * 100) / 100.0) + " hp");
        }
    }

    public static ChatColor getChatColorByHp(double hp, double maxhp) {
        if (hp / maxhp >= 0.5) {
            return ChatColor.GREEN;
        }
        if (hp / maxhp >= 0.25) {
            return ChatColor.YELLOW;
        } else {
            return ChatColor.RED;
        }
    }
}