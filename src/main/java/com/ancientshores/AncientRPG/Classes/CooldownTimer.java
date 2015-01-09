package com.ancientshores.AncientRPG.Classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ancientshores.AncientRPG.AncientRPG;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class CooldownTimer implements Serializable, Runnable, ConfigurationSerializable {
    private static final long serialVersionUID = 1L;
    public boolean ready;
    public long time;
    public final String cooldownname;
    public long oldtime = 0;
    public boolean enabled = false;
    public int id = 0;

    public CooldownTimer(int time, String cooldownname) {
        this.ready = true;
        this.time = time;
        this.cooldownname = cooldownname;
        oldtime = System.currentTimeMillis();
    }

    public CooldownTimer(Map<String, Object> map) {
        this.ready = (Boolean) map.get("ready");
        this.time = (Long) map.get("time");
        this.cooldownname = (String) map.get("cooldownname");
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
            return (cooldownname.equals(c.cooldownname));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return cooldownname.hashCode();
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ready", ready);
        map.put("time", time);
        map.put("cooldownname", cooldownname);
        map.put("oldtime", oldtime);
        map.put("enabled", enabled);
        map.put("id", id);
        return map;
    }
}