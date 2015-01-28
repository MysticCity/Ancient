package com.ancientshores.AncientRPG.HP;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ancientshores.AncientRPG.AncientRPG;

public class Armor {
	// Configuration for Armor durability
	private static FileConfiguration config;
	
	public static void damageArmor(ItemStack... armor) {
		for (int i = 0; i < armor.length; i++) {
			ItemStack part = armor[i];
			// ignore air
			if (part.getType() == Material.AIR) continue;
			
			if (part.hasItemMeta() && part.getItemMeta().hasLore()) {
				List<String> lore = part.getItemMeta().getLore();
				
				for (String l : lore) {
					// Get Lore starting with $a$r$p$g That shouldn't be visible ingame
					if (l.startsWith(ChatColor.COLOR_CHAR + "a" + ChatColor.COLOR_CHAR + "r" + ChatColor.COLOR_CHAR + "p" + ChatColor.COLOR_CHAR + "g")) {
						// divide into durability right now and maximum
						String[] values = l.replaceAll(ChatColor.COLOR_CHAR + "a" + ChatColor.COLOR_CHAR + "r" + ChatColor.COLOR_CHAR + "p" + ChatColor.COLOR_CHAR + "g" + ChatColor.GOLD + "Durability: ", "").split("/");
						
						// remove color codes with replace all (regex: §. to ""
						int current = Integer.parseInt(values[0].replaceAll(ChatColor.COLOR_CHAR + ".", ""));
						int max = getMaxDurabilityOfArmor(part.getType());
						
						current--;
						
						// Update lore. Format: Durability: current/max
						String newLore = ChatColor.COLOR_CHAR + "a" + ChatColor.COLOR_CHAR + "r" + ChatColor.COLOR_CHAR + "p" + ChatColor.COLOR_CHAR + "g" + ChatColor.GOLD + "Durability: " + ChatColor.AQUA + current + ChatColor.RESET + "/" + ChatColor.AQUA + max; 
						
						// Overwrite old lore line
						lore.set(lore.indexOf(l), newLore);
						
						// remove item if destroyed
						if (current == 0) {
							armor[i] = null;
//							System.out.println("KAPUTT" + part.getType());
//							part.setAmount(0);
//							entity.getEquipment();
//							part.setType(Material.AIR);
						}
						else {
							ItemMeta meta = part.getItemMeta();
							meta.setLore(lore);
							part.setItemMeta(meta);
							// else set durability also in minecraft
							short partDurability = (short) (part.getType().getMaxDurability() * (1 - ((double) current / max)));
							// if calculated durabilty equals maximum, but item isn't destroyed right now, set durability to max - 1
							part.setDurability((short) (partDurability != part.getType().getMaxDurability() ? partDurability : part.getType().getMaxDurability() - 1));
							
							
						}
						
						break;
					}
				}
			} else {
				List<String> lore = new ArrayList<String>();
				lore.add(ChatColor.COLOR_CHAR + "a" + ChatColor.COLOR_CHAR + "r" + ChatColor.COLOR_CHAR + "p" + ChatColor.COLOR_CHAR + "g" + ChatColor.GOLD + "Durability: " + ChatColor.AQUA + (getMaxDurabilityOfArmor(part.getType()) - 1) + ChatColor.RESET + "/" + ChatColor.AQUA + getMaxDurabilityOfArmor(part.getType()));
				ItemMeta meta = part.getItemMeta();
				meta.setLore(lore);
				part.setItemMeta(meta);
			}
		}
	}
	
	private static int getMaxDurabilityOfArmor(Material mat) {
		switch (mat) {
		case DIAMOND_HELMET: return config.getInt("Durability.Diamond.Durability of a diamond helmet");
		case DIAMOND_CHESTPLATE: return config.getInt("Durability.Diamond.Durability of a diamond chestplate");
		case DIAMOND_LEGGINGS: return config.getInt("Durability.Diamond.Durability of a diamond leggings");
		case DIAMOND_BOOTS: return config.getInt("Durability.Diamond.Durability of diamond boots");
				    
		case IRON_HELMET: return config.getInt("Durability.Iron.Durability of an iron helmet");
		case IRON_CHESTPLATE: return config.getInt("Durability.Iron.Durability of an iron chestplate");
		case IRON_LEGGINGS: return config.getInt("Durability.Iron.Durability of an iron leggings");
		case IRON_BOOTS: return config.getInt("Durability.Iron.Durability of iron boots");
		
		case GOLD_HELMET: return config.getInt("Durability.Gold.Durability of a gold helmet");
		case GOLD_CHESTPLATE: return config.getInt("Durability.Gold.Durability of a gold chestplate");
		case GOLD_LEGGINGS: return config.getInt("Durability.Gold.Durability of a gold leggings");
		case GOLD_BOOTS: return config.getInt("Durability.Gold.Durability of gold boots");
		
		case CHAINMAIL_HELMET: return config.getInt("Durability.Chain.Durability of a chain helmet");
		case CHAINMAIL_CHESTPLATE: return config.getInt("Durability.Chain.Durability of a chain chestplate");
		case CHAINMAIL_LEGGINGS: return config.getInt("Durability.Chain.Durability of a chain leggings");
		case CHAINMAIL_BOOTS: return config.getInt("Durability.Chain.Durability of chain boots");
		
		case LEATHER_HELMET: return config.getInt("Durability.Leather.Durability of a leather cap");
		case LEATHER_CHESTPLATE: return config.getInt("Durability.Leather.Durability of a leather tunic");
		case LEATHER_LEGGINGS: return config.getInt("Durability.Leather.Durability of a leather pants");
		case LEATHER_BOOTS: return config.getInt("Durability.Leather.Durability of leather boots");
		
		default: return mat.getMaxDurability();
		}
	}
	
	public static double getArmorReduction(LivingEntity attackedEntity) {
		double reduction = 0;
		if (attackedEntity.getEquipment().getHelmet() != null) {
			switch (attackedEntity.getEquipment().getHelmet().getType()) {
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
				default:
					break;
			}
		}
		
		if (attackedEntity.getEquipment().getChestplate() != null) {
			switch (attackedEntity.getEquipment().getChestplate().getType()) {
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
				default:
					break;
			}
		}
		if (attackedEntity.getEquipment().getLeggings() != null) {
			switch (attackedEntity.getEquipment().getLeggings().getType()) {
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
				default:
					break;
			}
		}
		if (attackedEntity.getEquipment().getBoots() != null) {
			switch (attackedEntity.getEquipment().getBoots().getType()) {
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
				default:
					break;
			}
		}
		return reduction;
	}
	
	public static void writeConfig() {
		File file = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "armorconfig.yml");
		try {
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void loadConfig() {
		File file = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "armorconfig.yml");
		if (!file.exists()) AncientRPG.plugin.saveResource("armorconfig.yml", true);
		
		config = YamlConfiguration.loadConfiguration(file);
	}

}
