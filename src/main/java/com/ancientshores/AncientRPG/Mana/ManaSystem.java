package com.ancientshores.AncientRPG.Mana;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ManaSystem implements ConfigurationSerializable {
    static {
        ConfigurationSerialization.registerClass(ManaSystem.class);
    }

    public static float defaultManaRegInterval = 3;
    public static int defaultMana = 1000;
    public static final int defaultReg = 20;
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
        this.playeruuid = UUID.fromString((String) map.get("uuid"));
        double d = (Double) map.get("manareginterval");
        this.manareginterval = (float) d;
        this.manareg = (Integer) map.get("manareg");
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
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(AncientRPG.plugin, new Runnable() {
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
    }

    public static void loadConfig(AncientRPG plugin) {
        File newconfig = new File(plugin.getDataFolder().getPath() + File.separator + "manaconfig.yml");
        if (newconfig.exists()) {
            YamlConfiguration yc = new YamlConfiguration();
            try {
                yc.load(newconfig);
            } catch (Exception e) {
                e.printStackTrace();
            }
            defaultMana = yc.getInt("Mana.default mana", defaultMana);
            defaultMana = yc.getInt("Mana.default manareg", defaultMana);
            defaultManaRegInterval = (float) yc.getDouble("Mana.manareg interval", defaultManaRegInterval);
        }
    }

    public static void writeConfig(AncientRPG plugin) {
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
        yc.set("Mana.default manareg", defaultMana);
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
    }

    public void stopRegenTimer() {
        Bukkit.getScheduler().cancelTask(task);
    }

    public void setMaxMana() {
        PlayerData pd = PlayerData.getPlayerData(playeruuid);
        AncientRPGClass mClass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
        if (mClass != null) {
            if (AncientRPGExperience.isEnabled() && AncientRPGExperience.isWorldEnabled(Bukkit.getServer().getPlayer(playeruuid))) {
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