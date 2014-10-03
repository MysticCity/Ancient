package com.ancientshores.AncientRPG.HP;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.Util.AncientRPGUUIDConverter;
import com.ancientshores.AncientRPG.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class AncientRPGHP implements Serializable, ConfigurationSerializable {
	private static final long serialVersionUID = 1L;
	public double maxhp;
	public double hpReg = DamageConverter.hpReg;
	public double hpRegInterval = DamageConverter.hpRegInterval;
	public double health;
	public UUID playerUUID;
	public int task;
	public long lastAttackDamage;
	public boolean damage;

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("maxhp", maxhp);
		map.put("hpReg", hpReg);
		map.put("hpRegInterval", hpRegInterval);
		map.put("hp", health);
		map.put("uuid", playerUUID.toString());

		return map;
	}

	public AncientRPGHP(Map<String, Object> map) {
		this.health = ((Number) map.get("hp")).intValue();
		this.maxhp = ((Number) map.get("maxhp")).intValue();
		this.hpRegInterval = ((Number) map.get("hpRegInterval")).intValue();
		this.hpReg = ((Number) map.get("hpReg")).intValue();
		
		if (map.containsKey("uuid")) {
			this.playerUUID = UUID.fromString((String) map.get("uuid"));
		}
		else {
			this.playerUUID = AncientRPGUUIDConverter.getUUID((String) map.get("playername"));//Bukkit.getOfflinePlayer((String) map.get("playername")).getUniqueId();
		}
		
	}

	public AncientRPGHP(int maxhp, UUID playeruuid) {
		this.health = maxhp;
		this.maxhp = maxhp;
		this.playerUUID = playeruuid;
	}

	public void startRegenTimer() {
		this.stopRegenTimer();
		if (!DamageConverter.isEnabled()) {
			return;
		}
		AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(playerUUID).getClassName().toLowerCase());
		if (mClass != null) {
			if (DamageConverter.isEnabled()) {
				hpReg = mClass.hpreglevel.get(PlayerData.getPlayerData(playerUUID).getXpSystem().level).intValue();
			} else {
				hpReg = mClass.hpreg;
			}
		} else {
			hpReg = DamageConverter.hpReg;
		}
		hpRegInterval = DamageConverter.hpRegInterval;
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(AncientRPG.plugin, new Runnable() {
			public void run() {
				if (!damage) {
					Player p = Bukkit.getPlayer(playerUUID);
					if (p == null) {
						stopRegenTimer();
						return;
					}
					if (p.isDead()) {
						return;
					}
					health = p.getHealth();
					if (p.getFoodLevel() >= DamageConverter.minFoodRegLevel) {
						if (DamageConverter.isWorldEnabled(p.getWorld())) {
							addHpByUUID(playerUUID, hpReg);
						}
					}
				}
				damage = false;
			}
		}, Math.round(hpRegInterval * 20), Math.round(hpRegInterval * 20));
	}

	public void stopRegenTimer() {
		Bukkit.getScheduler().cancelTask(task);
	}

	public void setMaxHp() {
		if (Bukkit.getPlayer(playerUUID) != null) {
			if (!DamageConverter.isWorldEnabled(Bukkit.getPlayer(playerUUID).getWorld())) {
				Bukkit.getPlayer(playerUUID).setMaxHealth(20);
				return;
			}
		}
		AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(playerUUID).getClassName().toLowerCase());
		if (mClass != null) {
			if (AncientRPGExperience.isEnabled()) {
				if (PlayerData.getPlayerData(playerUUID).getStance() != null && mClass.stances.containsKey(PlayerData.getPlayerData(playerUUID).getStance().toLowerCase())) {
					maxhp = mClass.stances.get(PlayerData.getPlayerData(playerUUID).getStance().toLowerCase()).hplevel.get(PlayerData.getPlayerData(playerUUID).getXpSystem().level).intValue();
				} else {
					maxhp = mClass.hplevel.get(PlayerData.getPlayerData(playerUUID).getXpSystem().level).intValue();
				}
			} else {
				if (mClass.stances.containsKey(PlayerData.getPlayerData(playerUUID).getStance())) {
					maxhp = mClass.stances.get(PlayerData.getPlayerData(playerUUID).getStance()).hp;
				} else {
					maxhp = mClass.hp;
				}
			}
		} else {
			maxhp = DamageConverter.standardhp;
		}
		if (health > maxhp) {
			health = maxhp;
		}
		addMinecraftHpByUUID(playerUUID, 0);
		Player p = Bukkit.getPlayer(playerUUID);
		if (p == null) {
			return;
		}
		p.setMaxHealth((int) maxhp);
	}

	public void setHpRegen() {
		AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(playerUUID).getClassName().toLowerCase());
		if (mClass != null) {
			if (AncientRPGExperience.isEnabled()) {
				if (PlayerData.getPlayerData(playerUUID).getStance() != null && mClass.stances.containsKey(PlayerData.getPlayerData(playerUUID).getStance().toLowerCase())) {
					hpReg = mClass.stances.get(PlayerData.getPlayerData(playerUUID).getStance().toLowerCase()).hpreglevel.get(PlayerData.getPlayerData(playerUUID).getXpSystem().level).intValue();
				} else {
					hpReg = mClass.hpreglevel.get(PlayerData.getPlayerData(playerUUID).getXpSystem().level).intValue();
				}
			} else {
				if (PlayerData.getPlayerData(playerUUID).getStance() != null && mClass.stances.containsKey(PlayerData.getPlayerData(playerUUID).getStance().toLowerCase())) {
					hpReg = mClass.stances.get(PlayerData.getPlayerData(playerUUID).getStance().toLowerCase()).hpreg;
				} else {
					hpReg = mClass.hpreg;
				}
			}
		} else {
			hpReg = DamageConverter.hpReg;
		}
	}

	public void setMinecraftHP() {
		if (health < 0) {
			health = 0;
		}
		if (health > maxhp) {
			health = maxhp;
		}
		Player mPlayer = Bukkit.getPlayer(playerUUID);
		if (Bukkit.getPlayer(playerUUID) == null) {
			return;
		}
		if (health > mPlayer.getMaxHealth()) {
			health = mPlayer.getMaxHealth();
		}
		mPlayer.setHealthScaled(true);
		if ((int) health == 0 && health > 0) {
			mPlayer.setHealth(1);
		} else {
			mPlayer.setHealth((int) health);
		}
	}

	public static void addHpByUUID(UUID uuid, double hp) {
		addNormalHp(uuid, hp);
		if (!DamageConverter.isEnabled()) {
			return;
		}
		AncientRPGHP hpinstance = PlayerData.getPlayerData(uuid).getHpsystem();
		if (hpinstance.health < 0) {
			return;
		}
		Player p = Bukkit.getPlayer(uuid);
		
		hpinstance.health = p.getHealth();
		hpinstance.setMinecraftHP();
	}

	public static void addMinecraftHpByUUID(UUID uuid, float hp) {
		AncientRPGHP hpinstance = PlayerData.getPlayerData(uuid).getHpsystem();
		if (hpinstance.health < 0) {
			return;
		}
		hpinstance.health += (float) hpinstance.maxhp * (hp / (float) 20);
		hpinstance.setMinecraftHP();
	}

	public static double getHpByMinecraftDamage(UUID uuid, double hp) {
		AncientRPGHP hpinstance = PlayerData.getPlayerData(uuid).getHpsystem();
		return (hpinstance.maxhp * (hp / 20));
	}

	public static void addNormalHp(UUID uuid, double hp) {
		Player p = Bukkit.getPlayer(uuid);

		if (p.getHealth() > 0) {
			if (p.getHealth() + hp > p.getMaxHealth()) {
				p.setHealth(p.getMaxHealth());
			} else {
				p.setHealth(p.getHealth() + hp);
			}
		}
	}
}