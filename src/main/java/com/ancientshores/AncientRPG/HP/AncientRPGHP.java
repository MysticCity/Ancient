package com.ancientshores.AncientRPG.HP;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AncientRPGHP implements Serializable, ConfigurationSerializable {
    private static final long serialVersionUID = 1L;
    public double maxhp;
    public double hpReg = DamageConverter.hpReg;
    public double hpRegInterval = DamageConverter.hpRegInterval;
    public double health;
    public final String playername;
    public int task;
    public long lastAttackDamage;
    public Player player;

    static {
        ConfigurationSerialization.registerClass(AncientRPGHP.class);
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("maxhp", maxhp);
        map.put("hpReg", hpReg);
        map.put("hpRegInterval", hpRegInterval);
        map.put("hp", health);
        map.put("playername", playername);
        return map;
    }

    public AncientRPGHP(Map<String, Object> map) {
        this.health = ((Number) map.get("hp")).intValue();
        this.maxhp = ((Number) map.get("maxhp")).intValue();
        this.playername = (String) map.get("playername");
        this.hpRegInterval = ((Number) map.get("hpRegInterval")).intValue();
        this.hpReg = ((Number) map.get("hpReg")).intValue();
    }

    public AncientRPGHP(int maxhp, String playername) {
        this.health = maxhp;
        this.maxhp = maxhp;
        this.playername = playername;
    }

    public boolean damage;

    public void startRegenTimer() {
        this.stopRegenTimer();
        if (!DamageConverter.isEnabled()) {
            return;
        }
        AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(playername).getClassName().toLowerCase());
        if (mClass != null) {
            if (DamageConverter.isEnabled()) {
                hpReg = mClass.hpreglevel.get(PlayerData.getPlayerData(playername).getXpSystem().level).intValue();
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
                    Player p = player;
                    if (p == null) {
                        stopRegenTimer();
                        return;
                    }
                    if (p.isDead()) {
                        return;
                    }
                    health = p.getHealth();
                    if (p.getFoodLevel() >= DamageConverter.minFoodRegLevel) {
                        if (DamageConverter.isEnabled() && DamageConverter.isWorldEnabled(p)) {
                            addHpByName(playername, hpReg);
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
        if (player != null) {
            if (!DamageConverter.isEnabled() || !DamageConverter.isEnabled(player.getWorld())) {
                player.setMaxHealth(20);
                return;
            }
        }
        AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(playername).getClassName().toLowerCase());
        if (mClass != null) {
            if (AncientRPGExperience.isEnabled()) {
                if (PlayerData.getPlayerData(playername).getStance() != null && mClass.stances.containsKey(PlayerData.getPlayerData(playername).getStance().toLowerCase())) {
                    maxhp = mClass.stances.get(PlayerData.getPlayerData(playername).getStance().toLowerCase()).hplevel.get(PlayerData.getPlayerData(playername).getXpSystem().level).intValue();
                } else {
                    maxhp = mClass.hplevel.get(PlayerData.getPlayerData(playername).getXpSystem().level).intValue();
                }
            } else {
                if (mClass.stances.containsKey(PlayerData.getPlayerData(playername).getStance())) {
                    maxhp = mClass.stances.get(PlayerData.getPlayerData(playername).getStance()).hp;
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
        addMinecraftHpByName(playername, 0);
        Player p = player;
        if (p == null) {
            return;
        }
        p.setMaxHealth((int) maxhp);
    }

    public void setHpRegen() {
        AncientRPGClass mClass = AncientRPGClass.classList.get(PlayerData.getPlayerData(playername).getClassName().toLowerCase());
        if (mClass != null) {
            if (AncientRPGExperience.isEnabled()) {
                if (PlayerData.getPlayerData(playername).getStance() != null && mClass.stances.containsKey(PlayerData.getPlayerData(playername).getStance().toLowerCase())) {
                    hpReg = mClass.stances.get(PlayerData.getPlayerData(playername).getStance().toLowerCase()).hpreglevel.get(PlayerData.getPlayerData(playername).getXpSystem().level).intValue();
                } else {
                    hpReg = mClass.hpreglevel.get(PlayerData.getPlayerData(playername).getXpSystem().level).intValue();
                }
            } else {
                if (PlayerData.getPlayerData(playername).getStance() != null && mClass.stances.containsKey(PlayerData.getPlayerData(playername).getStance().toLowerCase())) {
                    hpReg = mClass.stances.get(PlayerData.getPlayerData(playername).getStance().toLowerCase()).hpreg;
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
        Player mPlayer = player;
        if (player == null) {
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

    public static void addHpByName(String name, double hp) {
        addNormalHp(name, hp);
        if (!DamageConverter.isEnabled()) {
            return;
        }
        AncientRPGHP hpinstance = PlayerData.getPlayerData(name).getHpsystem();
        if (hpinstance.health < 0) {
            return;
        }
        Player p = AncientRPG.plugin.getServer().getPlayer(name);
        hpinstance.health = p.getHealth();
        hpinstance.setMinecraftHP();
    }

    public static void addMinecraftHpByName(String name, float hp) {
        AncientRPGHP hpinstance = PlayerData.getPlayerData(name).getHpsystem();
        if (hpinstance.health < 0) {
            return;
        }
        hpinstance.health += (float) hpinstance.maxhp * (hp / (float) 20);
        hpinstance.setMinecraftHP();
    }

    public static double getHpByMinecraftDamage(String name, double hp) {
        AncientRPGHP hpinstance = PlayerData.getPlayerData(name).getHpsystem();
        return (hpinstance.maxhp * (hp / 20));
    }

    public static void addNormalHp(String name, double hp) {
        Player p = AncientRPG.plugin.getServer().getPlayer(name);
        if (p.getHealth() > 0) {
            if (p.getHealth() + hp > p.getMaxHealth()) {
                p.setHealth(p.getMaxHealth());
            } else {
                p.setHealth(p.getHealth() + hp);
            }
        }
    }
}