package com.ancientshores.Ancient.Classes;

import java.io.Serializable;

import com.ancientshores.Ancient.Ancient;

public enum ClassType implements Serializable {
    Warrior(1000), Engineer(700), Standard(600);

    public static int warriorhp = 600;
    public static final String HpConfigWarriorHP = "HP.HP of a warrior";
    private final int maxhp;

    private ClassType(int maxhp) {
        this.maxhp = maxhp;
    }

    public int getMaxHp() {
        return maxhp;
    }

    public static void writeConfig(Ancient plugin) {
        plugin.getConfig().set(HpConfigWarriorHP, warriorhp);
    }

    public static void loadConfig(Ancient plugin) {
        warriorhp = plugin.getConfig().getInt(HpConfigWarriorHP, warriorhp);
    }
}