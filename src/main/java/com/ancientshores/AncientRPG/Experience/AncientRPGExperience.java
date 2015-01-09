package com.ancientshores.AncientRPG.Experience;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ancient.util.UUIDConverter;
import com.ancientshores.AncientRPG.API.AncientGainExperienceEvent;
import com.ancientshores.AncientRPG.API.AncientLevelupEvent;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import com.ancientshores.AncientRPG.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class AncientRPGExperience implements Serializable, ConfigurationSerializable {
	public static float multiplier = 1;
	private static final long serialVersionUID = 1L;
	public int level;
	public int xp;
	public final UUID uuid;
	public static final HashSet<UUID> alreadyDead = new HashSet<UUID>();
	
	// admin permission node
	public final static String nodeXPAdmin = "AncientRPG.XP.Admin";

	/* XP configuration
	 * and levels
	 * and where it is enabled
	 */
	private static FileConfiguration config;
	

	public AncientRPGExperience(UUID playeruuid) {
		level = 1;
		xp = 0;
		uuid = playeruuid;
	}

	public AncientRPGExperience(Map<String, Object> map) {
		this.level = (Integer) map.get("level");
		this.xp = (Integer) map.get("xp");
		if (map.containsKey("uuid")) this.uuid = UUID.fromString((String) map.get("uuid"));
		else this.uuid = UUIDConverter.getUUID((String) map.get("xpname"));
	}

	public static boolean isWorldEnabled(World w) {
		if (w == null) return false;
		if (!isEnabled()) return false;
		
		List<String> worlds = config.getStringList("XP.XPsystem enabled world");
		
		if (worlds == null || worlds.size() == 0) return true; // if enabled and no worlds in list its everywhere enabled
		if (worlds.size() == 1 && worlds.get(0).equalsIgnoreCase("")) return true; // if list is not empty but only has a blank line
		for (String s : worlds) 
			if (w.getName().equalsIgnoreCase(s)) return true;
		return false;
	}

	public void addXP(int xp, boolean party) {
		xp *= multiplier;
		Player p = AncientRPG.plugin.getServer().getPlayer(uuid);
		if (p == null || !isWorldEnabled(p.getWorld())) return;
		
		try {
			if (AncientRPGParty.splitxp && party) {
				AncientRPGParty mParty = AncientRPGParty.getPlayersParty(p.getUniqueId());
				if (mParty != null) {
					HashSet<UUID> playersInRange = new HashSet<UUID>();
					Collection<UUID> uuids = mParty.getMembers();
					for (UUID uuid : uuids) {
						if (uuid.compareTo(p.getUniqueId()) == 0) continue;
						
						if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
							Location loc = Bukkit.getPlayer(uuid).getLocation();
							if (loc.getWorld().equals(p.getWorld()) && Math.abs(loc.distance(p.getLocation())) < AncientRPGParty.splitxprange) playersInRange.add(uuid);
						}
					}
					int times = playersInRange.size() + 1;
					int newXp = (xp / times);
					xp = newXp;

					for (UUID uuid : playersInRange)
						PlayerData.getPlayerData(uuid).getXpSystem().addXP(newXp, false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AncientGainExperienceEvent gainevent = new AncientGainExperienceEvent(this.xp, uuid, xp);
		Bukkit.getServer().getPluginManager().callEvent(gainevent);
		if (gainevent.cancelled) return;
		
		this.xp += xp;
		int oldlevel = level;
		level = 1;
		for (int i = 1; i <= config.getInt("XP.max level"); i++) {
			if (this.xp >= getExperienceOfLevel(i)) level = i;
			else break;
			
		}
		if (level != oldlevel) {
			AncientLevelupEvent event = new AncientLevelupEvent(this.level, p.getUniqueId(), this.xp, xp);
			Bukkit.getServer().getPluginManager().callEvent(event);
			if (event.cancelled) {
				this.level = oldlevel;
				return;
			}
			PlayerData.getPlayerData(uuid).getHpsystem().setMaxHP();
			PlayerData.getPlayerData(uuid).getManasystem().setMaxMana();
				/* AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(p.getName()).getClassName().toLowerCase());
				if (mClass != null)
				{
					PlayerData.getPlayerData(p.getName()).getHpsystem().hpReg = mClass.hpreglevel.get(level).intValue();
				}
				if (AncientRPGExperience.isEnabled())
				{
					PlayerData pd = PlayerData.getPlayerData(name);
					AncientRPGClass newClass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
					if (newClass != null)
					{
						pd.getHpsystem().maxhp = newClass.hplevel.get(pd.getXpSystem().level).intValue();
						pd.getHpsystem().hpReg = newClass.hpreglevel.get(pd.getXpSystem().level).intValue();
					}
					pd.getManasystem().setMaxMana();
				}
				*/
			p.sendMessage(ChatColor.GOLD + "[" + AncientRPG.brand + "] " + ChatColor.YELLOW + "You reached level " + level);
		}
	}

	public void recalculateLevel() {
		for (int i = 1; i <= config.getInt("XP.max level"); i++) {
			if (this.xp >= getExperienceOfLevel(i)) {
				level = i;
			} else {
				break;
			}
		}
	}

	public static void processCommand(CommandSender sender, String[] args) {
		if (args.length > 1 && args[0].equalsIgnoreCase("setxp")) {
			SetXpCommand.setXp(sender, args);
			return;
		}
		if (args.length > 1 && args[0].equalsIgnoreCase("addxp")) {
			AddXpCommand.addXp(sender, args);
			return;
		}
		if (args.length > 1 && args[0].equalsIgnoreCase("setxpmultiplier")) {
			SetXpMultiplierCommand.setXpMultiplier(sender, args);
			return;
		}
		if (!(sender instanceof Player) || !isWorldEnabled(((Player) sender).getWorld())) {
			return;
		}
		Player p = (Player) sender;
		PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
		p.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
		p.sendMessage(ChatColor.GOLD + "[" + AncientRPG.brand + "] " + ChatColor.YELLOW + "Experience Information");
		p.sendMessage("You are level " + pd.getXpSystem().level);
		p.sendMessage("You have already " + pd.getXpSystem().xp + " XP");
		int xpofcurrentLevel = pd.getXpSystem().xp - pd.getXpSystem().getExperienceOfLevel(pd.getXpSystem().level);
		p.sendMessage("You have " + xpofcurrentLevel + " of the current level");
		if (pd.getXpSystem().level < config.getInt("XP.max level")) {
			int xpOfNextLevel = pd.getXpSystem().getExperienceOfLevel(pd.getXpSystem().level + 1) - pd.getXpSystem().xp;
			p.sendMessage("To reach the next level you need " + xpOfNextLevel + " more xp");
		}
		p.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
	}

	public int getExperienceOfLevel(int level) {
		return config.getInt("XP.Experience of level " + level);
	}
	public static void writeConfig(AncientRPG plugin) {
		File file = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "xpconfig.yml");
		try {
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
//		for (int i = 1; i <= MaxLevel; i++) {
//			if (xpConfig.get(("XP.Experience of level " + i)) == null) {
//				if (levelMap.containsKey(i)) {
//					xpConfig.set(("XP.Experience of level " + i), levelMap.get(i));
//				} else {
//					xpConfig.set(("XP.Experience of level " + i), 600 * (i - 1));
//				}
//			}
//		}
//		try {
//			xpConfig.save(newfile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static void loadConfig(AncientRPG plugin) {
		File file = new File(AncientRPG.plugin.getDataFolder().getPath() + File.separator + "xpconfig.yml");
		if (!file.exists()) AncientRPG.plugin.saveResource("xpconfig.yml", true);
		
		config = YamlConfiguration.loadConfiguration(file);
//		MaxLevel = config.getInt(XPConfigMaxLevel, MaxLevel);
//		for (int i = 1; i <= MaxLevel; i++) {
//			levelMap.put(i, yc.getInt(("XP.Experience of level " + i), 600 * (i - 1)));
//		}
	}

	public static void processEntityDamageByEntityEvent(final EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (damager instanceof Projectile) {
			damager = (Entity) ((Projectile) event.getDamager()).getShooter();
		}
		if (event.getCause() == DamageCause.CUSTOM) {
			return;
		}
		final Entity victim = event.getEntity();
		if (damager instanceof Player && !alreadyDead.contains(victim.getUniqueId()) && event != null) {
			if (victim instanceof LivingEntity && ((LivingEntity) victim).getHealth() - event.getDamage() <= 0) {
				alreadyDead.add(victim.getUniqueId());
				Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
					@Override
					public void run() {
						alreadyDead.remove(victim.getUniqueId());
					}

				}, 500);
				Player mPlayer = (Player) damager;
				int xp = getXPOfEntity(victim);
				PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
				pd.getXpSystem().addXP(xp, true);
			}
		}
	}

	public static int getXPOfEntity(Entity entity) {
		if (entity instanceof CaveSpider) return config.getInt("XP.Experience of cave spider");
		else if (entity instanceof Spider) return config.getInt("XP.Experience of spider");
		else if (entity instanceof Skeleton) return config.getInt("XP.Experience of skeleton");
		else if (entity instanceof Creeper) return config.getInt("XP.Experience of creeper");
		else if (entity instanceof Enderman) return config.getInt("XP.Experience of enderman");
		else if (entity instanceof PigZombie) return config.getInt("XP.Experience of pigzombie");
		else if (entity instanceof Zombie) return config.getInt("XP.Experience of zombie");
		else if (entity instanceof Ghast) return config.getInt("XP.Experience of ghast");
		else if (entity instanceof Slime) return config.getInt("XP.Experience of slime");
		else if (entity instanceof Wolf) return config.getInt("XP.Experience of wolf");
		else if (entity instanceof Giant) return config.getInt("XP.Experience of giant");
		else if (entity instanceof EnderDragon) return config.getInt("XP.Experience of enderdragon");
		else if (entity instanceof Player) return config.getInt("XP.Experience of player");
		else if (entity instanceof Ocelot) return config.getInt("XP.Experience of ocelot");
		else if (entity instanceof Snowman) return config.getInt("XP.Experience of snowman");
		else if (entity instanceof IronGolem) return config.getInt("XP.Experience of iron golem");
		else if (entity instanceof Silverfish) return config.getInt("XP.Experience of silverfish");
		else if (entity instanceof Witch) return config.getInt("XP.Experience of witch");
		else if (entity instanceof Wither) return config.getInt("XP.Experience of wither");
		else if (entity instanceof Blaze) return config.getInt("XP.Experience of blaze");
		return 0;
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("level", this.level);
		map.put("xp", this.xp);
		map.put("uuid", this.uuid.toString());

		return map;
	}

	public static boolean isEnabled() {
		return config.getBoolean("XP.XPsystem enabled");
	}

	public static int getXPOfStone() {
		return config.getInt("XP.Experience of stone");
	}
	
	public static int getXPOfCoal() {
		return config.getInt("XP.Experience of coal");
	}
	
	public static int getXPOfIron() {
		return config.getInt("XP.Experience of iron");
	}
	
	public static int getXPOfLapis() {
		return config.getInt("XP.Experience of lapis");
	}
	
	public static int getXPOfGold() {
		return config.getInt("XP.Experience of gold");
	}
	
	public static int getXPOfDiamond() {
		return config.getInt("XP.Experience of diamond");
	}
	
	public static int getXPOfRedstone() {
		return config.getInt("XP.Experience of redstone");
	}
	
	public static int getXPOfGlowstone() {
		return config.getInt("XP.Experience of glowstone");
	}
	
	public static int getXPOfNetherrack() {
		return config.getInt("XP.Experience of netherrack");
	}
	
	public static int getXPOfWood() {
		return config.getInt("XP.Experience of wood");
	}

	public static int getLevelCount() {
		return config.getInt("XP.max level");
	}
	
}