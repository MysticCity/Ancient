package com.ancientshores.Ancient.Mana;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ancient.util.UUIDConverter;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Display.Display;
import com.ancientshores.Ancient.Experience.AncientExperience;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ManaSystem implements ConfigurationSerializable {
    
    public static float defaultManaRegInterval = 3;
    public static int defaultMana = 1000;
    public static int defaultReg = 20;
    public static boolean enabled = true;
    public int maxmana;
    public int curmana;
    public float manareginterval = defaultManaRegInterval;
    public int manareg = defaultMana;
    private UUID playeruuid;
    public int task;

    public ManaSystem(UUID playeruuid, int maxmana) {
        this.playeruuid = playeruuid;
        this.maxmana = maxmana;
        this.curmana = maxmana;
    }

    public ManaSystem(Map<String, Object> map) {
        this.curmana = (Integer) map.get("mana");
        this.maxmana = (Integer) map.get("maxmana");
        double d = (Double) map.get("manareginterval");
        this.manareginterval = (float) d;
        this.manareg = (Integer) map.get("manareg");
        
        if (map.containsKey("uuid")) {
        	this.playeruuid = UUID.fromString((String) map.get("uuid"));
        }
        else {
        	this.playeruuid = UUIDConverter.getUUID((String) map.get("playername"));
        }
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("maxmana", maxmana);
        map.put("manareg", manareg);
        map.put("manareginterval", manareginterval);
        map.put("mana", curmana);
        map.put("uuid", playeruuid.toString());
        
        return map;
    }

    public void startRegenTimer() {
        this.stopRegenTimer();
        manareginterval = defaultManaRegInterval;
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Ancient.plugin, new Runnable() {
            public void run() {
                Player p = Bukkit.getPlayer(playeruuid);
                if (p == null) {
                    stopRegenTimer();
                    return;
                }
                if (p.isDead()) {
                    return;
                }
                addManaByUUID(playeruuid, manareg);
            }
        }, Math.round(manareginterval * 20), Math.round(manareginterval * 20));
    }

    public int getMaxmana() {
        return maxmana;
    }

    public int getCurrentMana() {
        return curmana;
    }

    public float getManareginterval() {
        return manareginterval;
    }

    public int getManareg() {
        return manareg;
    }

    public UUID getPlayerUUID() {
    	return playeruuid;
    }

    public static void addManaByUUID(UUID uuid, int amount) {
        PlayerData pd = PlayerData.getPlayerData(uuid);
        pd.getManasystem().curmana += amount;
        if (pd.getManasystem().maxmana < pd.getManasystem().curmana) {
            pd.getManasystem().curmana = pd.getManasystem().maxmana;
        }
        if (0 > pd.getManasystem().curmana) {
            pd.getManasystem().curmana = 0;
        }
        
        Display.updateMana(pd);
    }

    public static void removeManaByUUID(UUID uuid, int amount) {
        PlayerData pd = PlayerData.getPlayerData(uuid);
        pd.getManasystem().curmana -= amount;
        if (pd.getManasystem().maxmana < pd.getManasystem().curmana) {
            pd.getManasystem().curmana = pd.getManasystem().maxmana;
        }
        if (0 > pd.getManasystem().curmana) {
            pd.getManasystem().curmana = 0;
        }
        Display.updateMana(pd);
       }

    public static void loadConfig(Ancient plugin) {
        File newconfig = new File(plugin.getDataFolder().getPath() + File.separator + "manaconfig.yml");
        if (newconfig.exists()) {
            YamlConfiguration yc = new YamlConfiguration();
            try {
                yc.load(newconfig);
            } catch (Exception e) {
                e.printStackTrace();
            }
            defaultMana = yc.getInt("Mana.default mana", defaultMana);
            defaultReg = yc.getInt("Mana.default manareg", defaultReg);
            defaultManaRegInterval = (float) yc.getDouble("Mana.manareg interval", defaultManaRegInterval);
        }
    }

    public static void writeConfig(Ancient plugin) {
        File newconfig = new File(plugin.getDataFolder().getPath() + File.separator + "manaconfig.yml");
        if (!newconfig.exists()) {
            try {
                newconfig.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration yc = new YamlConfiguration();
        yc.set("Mana.default mana", defaultMana);
        yc.set("Mana.default manareg", defaultReg);
        yc.set("Mana.manareg interval", defaultManaRegInterval);
        try {
            yc.save(newconfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setManaByUUID(UUID uuid, int amount) {
        PlayerData pd = PlayerData.getPlayerData(uuid);
        pd.getManasystem().curmana = amount;
        if (pd.getManasystem().maxmana < pd.getManasystem().curmana) {
            pd.getManasystem().curmana = pd.getManasystem().maxmana;
        }
        if (0 > pd.getManasystem().curmana) {
            pd.getManasystem().curmana = 0;
        }
        Display.updateMana(pd);
    }

    public void stopRegenTimer() {
        Bukkit.getScheduler().cancelTask(task);
    }

    public void setMaxMana() {
        PlayerData pd = PlayerData.getPlayerData(playeruuid);
        AncientClass mClass = AncientClass.classList.get(pd.getClassName().toLowerCase());
        if (mClass != null) {
        	OfflinePlayer p = Bukkit.getOfflinePlayer(playeruuid);
        	
        	if (p.isOnline() && AncientExperience.isWorldEnabled(p.getPlayer().getWorld())) {
                maxmana = mClass.manalevel.get(pd.getXpSystem().level);
                manareg = mClass.manareglevel.get(pd.getXpSystem().level);
            } else {
                maxmana = mClass.defaultmana;
                manareg = mClass.manareg;
            }
        } else {
            maxmana = defaultMana;
            manareg = defaultReg;
        }
        if (curmana > maxmana) {
            curmana = maxmana;
        }
    }

    public static void processCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            ManaCommand.processManaCommand(sender);
        }
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent event) {
        if (playeruuid.compareTo(event.getPlayer().getUniqueId()) == 0) {
            setMaxMana();
            startRegenTimer();
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
    	if (playeruuid.compareTo(event.getPlayer().getUniqueId()) == 0) {
            stopRegenTimer();
        }
    }
}