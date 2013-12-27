package com.ancientshores.AncientRPG.Classes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BindingData implements Serializable, ConfigurationSerializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public boolean spoutstack;
    public String spoutname = "";
    public final int id;
    public byte data;

    static {
        ConfigurationSerialization.registerClass(BindingData.class);
    }

    public BindingData(Map<String, Object> map) {
        spoutstack = (Boolean) map.get("spoutstack");
        spoutname = (String) map.get("spoutname");
        id = (Integer) map.get("id");
        data = (Byte) map.get("data");
        if (isBreakable(id)) {
            data = 0;
        }
    }

    public BindingData(ItemStack is) {
        id = is.getTypeId();
        data = is.getData().getData();
        if (isBreakable(id)) {
            data = 0;
        }
    }

    public BindingData(int id, byte data) {
        this.id = id;
        this.data = data;
        if (isBreakable(id)) {
            this.data = 0;
        }
    }

    @Override
    public boolean equals(Object c) {
        if (c instanceof BindingData) {
            BindingData bd = ((BindingData) c);
            if (bd.id == this.id && bd.data == bd.data) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("spoutstack", true);
        map.put("spoutname", spoutname);
        map.put("id", id);
        map.put("data", data);
        return map;
    }

    @Override
    public int hashCode() {
        return id * 319 + data * 477;
    }

    public boolean isBreakable(int id) {
        if (id >= 256 && id <= 258) {
            return true;
        }
        if (id >= 267 && id <= 279) {
            return true;
        }
        if (id >= 283 && id <= 286) {
            return true;
        }
        if (id >= 290 && id <= 294) {
            return true;
        }
        if (id >= 298 && id <= 317) {
            return true;
        }
        if (id == 346) {
            return true;
        }
        if (id == 359) {
            return true;
        }
        return id == 261;
    }
}
