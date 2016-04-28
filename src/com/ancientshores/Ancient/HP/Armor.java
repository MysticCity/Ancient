package com.ancientshores.Ancient.HP;

import com.ancientshores.Ancient.Ancient;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Armor
{
  private static FileConfiguration config;
  private static Map<UUID, ItemStack[]> changedArmor = new HashMap();
  
  public static void damageArmor(ItemStack[] armor)
  {
    for (int i = 0; i < armor.length; i++)
    {
      ItemStack part = armor[i];
      if ((part != null) && (part.getType() != Material.AIR))
      {
        List<String> lore;
        if ((part.hasItemMeta()) && (part.getItemMeta().hasLore()))
        {
          lore = part.getItemMeta().getLore();
          for (String l : lore) {
            if (l.startsWith("§a§r§p§g"))
            {
              String[] values = l.replaceAll("§a§r§p§g" + ChatColor.GOLD + "Durability: ", "").split("/");
              
              int current = Integer.parseInt(values[0].replaceAll("§.", ""));
              int max = getMaxDurabilityOfArmor(part.getType());
              if (max == -1) {
                return;
              }
              int dmgPossibility = 100;
              for (Map.Entry<Enchantment, Integer> entry : part.getEnchantments().entrySet()) {
                if (((Enchantment)entry.getKey()).getName().equals(Enchantment.DURABILITY.getName())) {
                  dmgPossibility = 80 - 5 * (((Integer)entry.getValue()).intValue() - 1);
                }
              }
              current -= ((int)(Math.random() * 100.0D) < dmgPossibility ? 1 : 0);
              
              String newLore = "§a§r§p§g" + ChatColor.GOLD + "Durability: " + ChatColor.AQUA + current + ChatColor.RESET + "/" + ChatColor.AQUA + max;
              
              lore.set(lore.indexOf(l), newLore);
              if (current == 0)
              {
                armor[i] = null; break;
              }
              ItemMeta meta = part.getItemMeta();
              meta.setLore(lore);
              part.setItemMeta(meta);
              
              short partDurability = (short)(int)(part.getType().getMaxDurability() * (1.0D - current / max));
              
              part.setDurability((short)(partDurability != part.getType().getMaxDurability() ? partDurability : part.getType().getMaxDurability() - 1));
              
              break;
            }
          }
        }
        else
        {
          List<String> lore = new ArrayList();
          int max = getMaxDurabilityOfArmor(part.getType());
          int current = (int)(max * (1.0D - part.getDurability() / part.getType().getMaxDurability()));
          if (max == -1) {
            return;
          }
          int dmgPossibility = 100;
          for (Map.Entry<Enchantment, Integer> entry : part.getEnchantments().entrySet()) {
            if (((Enchantment)entry.getKey()).getName().equals(Enchantment.DURABILITY.getName())) {
              dmgPossibility = 80 - 5 * (((Integer)entry.getValue()).intValue() - 1);
            }
          }
          current -= ((int)(Math.random() * 100.0D) < dmgPossibility ? 1 : 0);
          lore.add("§a§r§p§g" + ChatColor.GOLD + "Durability: " + ChatColor.AQUA + current + ChatColor.RESET + "/" + ChatColor.AQUA + max);
          ItemMeta meta = part.getItemMeta();
          meta.setLore(lore);
          part.setItemMeta(meta);
        }
      }
    }
  }
  
  private static int getMaxDurabilityOfArmor(Material mat)
  {
    switch (mat)
    {
    case DIAMOND_HELMET: 
      return config.getInt("Durability.Diamond.Durability of a diamond helmet");
    case DIAMOND_CHESTPLATE: 
      return config.getInt("Durability.Diamond.Durability of a diamond chestplate");
    case DIAMOND_LEGGINGS: 
      return config.getInt("Durability.Diamond.Durability of a diamond leggings");
    case DIAMOND_BOOTS: 
      return config.getInt("Durability.Diamond.Durability of diamond boots");
    case IRON_HELMET: 
      return config.getInt("Durability.Iron.Durability of an iron helmet");
    case IRON_CHESTPLATE: 
      return config.getInt("Durability.Iron.Durability of an iron chestplate");
    case IRON_LEGGINGS: 
      return config.getInt("Durability.Iron.Durability of an iron leggings");
    case IRON_BOOTS: 
      return config.getInt("Durability.Iron.Durability of iron boots");
    case GOLD_HELMET: 
      return config.getInt("Durability.Gold.Durability of a gold helmet");
    case GOLD_CHESTPLATE: 
      return config.getInt("Durability.Gold.Durability of a gold chestplate");
    case GOLD_LEGGINGS: 
      return config.getInt("Durability.Gold.Durability of a gold leggings");
    case GOLD_BOOTS: 
      return config.getInt("Durability.Gold.Durability of gold boots");
    case CHAINMAIL_HELMET: 
      return config.getInt("Durability.Chain.Durability of a chain helmet");
    case CHAINMAIL_CHESTPLATE: 
      return config.getInt("Durability.Chain.Durability of a chain chestplate");
    case CHAINMAIL_LEGGINGS: 
      return config.getInt("Durability.Chain.Durability of a chain leggings");
    case CHAINMAIL_BOOTS: 
      return config.getInt("Durability.Chain.Durability of chain boots");
    case LEATHER_HELMET: 
      return config.getInt("Durability.Leather.Durability of a leather cap");
    case LEATHER_CHESTPLATE: 
      return config.getInt("Durability.Leather.Durability of a leather tunic");
    case LEATHER_LEGGINGS: 
      return config.getInt("Durability.Leather.Durability of a leather pants");
    case LEATHER_BOOTS: 
      return config.getInt("Durability.Leather.Durability of leather boots");
    }
    return -1;
  }
  
  public static double getArmorReduction(LivingEntity attackedEntity)
  {
    double reduction = 0.0D;
    if (attackedEntity.getEquipment().getHelmet() != null) {
      switch (attackedEntity.getEquipment().getHelmet().getType())
      {
      case DIAMOND_HELMET: 
        reduction += config.getDouble("Reduction.Diamond.Damage reduction of a diamond helmet in percentage points");
        break;
      case IRON_HELMET: 
        reduction += config.getDouble("Reduction.Iron.Damage reduction of an iron helmet in percentage points");
        break;
      case GOLD_HELMET: 
        reduction += config.getDouble("Reduction.Gold.Damage reduction of a gold helmet in percentage points");
        break;
      case LEATHER_HELMET: 
        reduction += config.getDouble("Reduction.Leather.Damage reduction of a leather cap in percentage points");
        break;
      case CHAINMAIL_HELMET: 
        reduction += config.getDouble("Reduction.Chain.Damage reduction of a chain helmet in percentage points");
        break;
      }
    }
    if (attackedEntity.getEquipment().getChestplate() != null) {
      switch (attackedEntity.getEquipment().getChestplate().getType())
      {
      case DIAMOND_CHESTPLATE: 
        reduction += config.getDouble("Reduction.Diamond.Damage reduction of a diamond chestplate in percentage points");
        break;
      case IRON_CHESTPLATE: 
        reduction += config.getDouble("Reduction.Iron.Damage reduction of an iron chestplate in percentage points");
        break;
      case GOLD_CHESTPLATE: 
        reduction += config.getDouble("Reduction.Gold.Damage reduction of a gold chestplate in percentage points");
        break;
      case LEATHER_CHESTPLATE: 
        reduction += config.getDouble("Reduction.Leather.Damage reduction of a leather tunic in percentage points");
        break;
      case CHAINMAIL_CHESTPLATE: 
        reduction += config.getDouble("Reduction.Chain.Damage reduction of a chain chestplate in percentage points");
        break;
      }
    }
    if (attackedEntity.getEquipment().getLeggings() != null) {
      switch (attackedEntity.getEquipment().getLeggings().getType())
      {
      case DIAMOND_LEGGINGS: 
        reduction += config.getDouble("Reduction.Diamond.Damage reduction of a diamond leggings in percentage points");
        break;
      case IRON_LEGGINGS: 
        reduction += config.getDouble("Reduction.Iron.Damage reduction of an iron leggings in percentage points");
        break;
      case GOLD_LEGGINGS: 
        reduction += config.getDouble("Reduction.Gold.Damage reduction of a gold leggings in percentage points");
        break;
      case LEATHER_LEGGINGS: 
        reduction += config.getDouble("Reduction.Leather.Damage reduction of a leather pants in percentage points");
        break;
      case CHAINMAIL_LEGGINGS: 
        reduction += config.getDouble("Reduction.Chain.Damage reduction of a chain leggings in percentage points");
        break;
      }
    }
    if (attackedEntity.getEquipment().getBoots() != null) {
      switch (attackedEntity.getEquipment().getBoots().getType())
      {
      case DIAMOND_BOOTS: 
        reduction += config.getDouble("Reduction.Diamond.Damage reduction of diamond boots in percentage points");
        break;
      case IRON_BOOTS: 
        reduction += config.getDouble("Reduction.Iron.Damage reduction of iron boots in percentage points");
        break;
      case GOLD_BOOTS: 
        reduction += config.getDouble("Reduction.Gold.Damage reduction of gold boots in percentage points");
        break;
      case LEATHER_BOOTS: 
        reduction += config.getDouble("Reduction.Leather.Damage reduction of leather boots in percentage points");
        break;
      case CHAINMAIL_BOOTS: 
        reduction += config.getDouble("Reduction.Chain.Damage reduction of chain boots in percentage points");
        break;
      }
    }
    return reduction;
  }
  
  public static void writeConfig()
  {
    File file = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "armorconfig.yml");
    try
    {
      config.save(file);
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }
  
  public static void loadConfig()
  {
    File file = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "armorconfig.yml");
    if (!file.exists()) {
      Ancient.plugin.saveResource("armorconfig.yml", true);
    }
    config = YamlConfiguration.loadConfiguration(file);
  }
  
  public static boolean hasChangedArmor(LivingEntity entity)
  {
    return changedArmor.containsKey(entity.getUniqueId());
  }
  
  public static ItemStack[] getChangedArmor(LivingEntity entity)
  {
    return (ItemStack[])changedArmor.get(entity.getUniqueId());
  }
  
  public static void addChangedArmor(LivingEntity entity, ItemStack[] armor)
  {
    changedArmor.remove(entity.getUniqueId());
    
    boolean validItemIncluded = false;
    for (ItemStack is : armor) {
      if ((is != null) && (is.getType() != Material.AIR))
      {
        validItemIncluded = true;
        break;
      }
    }
    if (validItemIncluded) {
      changedArmor.put(entity.getUniqueId(), armor);
    }
  }
  
  public static void removeChangedArmor(LivingEntity entity)
  {
    changedArmor.remove(entity.getUniqueId());
  }
}
