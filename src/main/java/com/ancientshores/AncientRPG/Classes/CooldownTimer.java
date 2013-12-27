package com.ancientshores.AncientRPG.Classes;

import com.ancientshores.AncientRPG.AncientRPG;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CooldownTimer implements Serializable, Runnable, ConfigurationSerializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public boolean ready;
    public long time;
    public final String name;
    public long oldtime = 0;
    public boolean enabled = false;
    public int id = 0;


    static {
        ConfigurationSerialization.registerClass(CooldownTimer.class);
    }


    public CooldownTimer(int time, String name) {
        this.ready = true;
        this.time = time;
        this.name = name;
        oldtime = System.currentTimeMillis();
    }

    public CooldownTimer(Map<String, Object> map) {
        this.ready = (Boolean) map.get("ready");
        this.time = (Long) map.get("time");
        this.name = (String) map.get("name");
        this.oldtime = (Long) map.get("oldtime");
        this.enabled = (Boolean) map.get("enabled");
        this.id = (Integer) map.get("id");
    }

    public void startTimer() {
        ready = false;
        if (!enabled) {
            id = Bukkit.getScheduler().scheduleSyncRepeatingTask(AncientRPG.plugin, this, 1, 1);
            enabled = true;
        }
    }

    @Override
    public void run() {
        time -= Math.abs(oldtime - System.currentTimeMillis());
        oldtime = System.currentTimeMillis();
        if (time <= 0) {
            time = 0;
            enabled = false;
            ready = true;
            Bukkit.getScheduler().cancelTask(id);
        }
    }

    @Override
    public boolean equals(Object obj) {
        try {
            CooldownTimer c = (CooldownTimer) obj;
            return name.equals(c.name);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ready", ready);
        map.put("time", time);
        map.put("name", name);
        map.put("oldtime", oldtime);
        map.put("enabled", enabled);
        map.put("id", id);
        return map;
    }
}
