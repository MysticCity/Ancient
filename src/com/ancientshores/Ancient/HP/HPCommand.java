package com.ancientshores.Ancient.HP;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HPCommand
{
  public static void showHP(Player p)
  {
    if (DamageConverter.isEnabledInWorld(p.getWorld()))
    {
      PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
      double hp = p.getHealth();
      double maxhp = p.getMaxHealth();
      p.sendMessage(Ancient.brand2 + "You have " + getChatColorByHp(hp, maxhp) + Math.round(hp * 100.0D) / 100.0D + "/" + Math.round(maxhp * 100.0D) / 100.0D + " hp");
      p.sendMessage(Ancient.brand2 + "Your hp regeneration per " + pd.getHpsystem().hpRegInterval + " seconds is " + ChatColor.GREEN + pd.getHpsystem().getHPRegen());
    }
    else
    {
      double hp = p.getHealth();
      double maxhp = p.getMaxHealth();
      p.sendMessage(Ancient.brand2 + "You have " + getChatColorByHp(hp, maxhp) + Math.round(hp * 100.0D) / 100.0D + "/" + Math.round(maxhp * 100.0D) / 100.0D + " hp");
    }
  }
  
  public static ChatColor getChatColorByHp(double hp, double maxhp)
  {
    if (hp / maxhp >= 0.5D) {
      return ChatColor.GREEN;
    }
    if (hp / maxhp >= 0.25D) {
      return ChatColor.YELLOW;
    }
    return ChatColor.RED;
  }
}
