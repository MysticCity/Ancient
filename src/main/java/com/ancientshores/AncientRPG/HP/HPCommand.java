package com.ancientshores.AncientRPG.HP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;

public class HPCommand {
    public static void showHP(Player p) {
        if (DamageConverter.isWorldEnabled(p.getWorld())) {
            PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
            double hp = p.getHealth();
            double maxhp = p.getMaxHealth();
            p.sendMessage(AncientRPG.brand2 + "You have " + getChatColorByHp(hp, maxhp) + hp + "/" + maxhp + " hp");
            p.sendMessage(AncientRPG.brand2 + "Your hp regeneration per " + pd.getHpsystem().hpRegInterval + " seconds is " + ChatColor.GREEN + pd.getHpsystem().hpReg);
        } else {
            double hp = p.getHealth();
            double maxhp = p.getMaxHealth();
            p.sendMessage(AncientRPG.brand2 + "You have " + getChatColorByHp(hp, maxhp) + hp + "/" + maxhp + " hp");
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