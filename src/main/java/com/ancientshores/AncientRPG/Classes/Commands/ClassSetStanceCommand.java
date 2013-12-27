package com.ancientshores.AncientRPG.Classes.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.BindingData;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ClassSetStanceCommand {
    public static void setStanceCommand(String[] args, Player p) {
        if (args.length == 1) {
            p.sendMessage(AncientRPG.brand2 + "Not enough arguments");
            return;
        }
        if (AncientRPGClass.playersOnCd.containsKey(p.getName())) {
            long t = System.currentTimeMillis();
            long div = t - AncientRPGClass.playersOnCd.get(p.getName());
            if (div < (AncientRPGClass.changeCd * 1000)) {
                p.sendMessage(AncientRPG.brand2 + "The class change cooldown hasn't expired yet");
                long timeleft = AncientRPGClass.playersOnCd.get(p.getName()) + (AncientRPGClass.changeCd * 1000) - System.currentTimeMillis();
                int minutes = (int) ((((double) timeleft) / 1000 / 60) + 1);
                p.sendMessage("You have to wait another " + minutes + " minutes.");
                return;
            }
        }
        PlayerData pd = PlayerData.getPlayerData(p.getName());
        Player csender = p;
        if (args.length == 3 && AncientRPG.hasPermissions(csender, "AncientRPG.classes.admin")) {
            Player pl = AncientRPG.plugin.getServer().getPlayer(args[1]);
            if (pl != null) {
                pd = PlayerData.getPlayerData(p.getName());
                p = pl;
                args[1] = args[2];
            } else {
                p.sendMessage(AncientRPG.brand2 + "Player not found");
                return;
            }
        }
        AncientRPGClass rootclass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
        if (rootclass == null) {
            p.sendMessage(AncientRPG.brand2 + "You need a class to set a stance!");
            return;
        }
        AncientRPGClass oldstance = rootclass.stances.get(pd.getStance());
        AncientRPGClass stance = rootclass.stances.get(args[1].toLowerCase());
        if (stance != null) {
            setStance(oldstance, stance, rootclass, p, csender);
        } else {
            p.sendMessage(AncientRPG.brand2 + "This stance does not exist (typo?)");
        }
    }

    public static boolean canSetStanceClass(AncientRPGClass newClass, final Player p) {
        PlayerData pd = PlayerData.getPlayerData(p.getName());
        if (AncientRPGExperience.isEnabled() && AncientRPGExperience.isWorldEnabled(p)) {
            if (pd.getXpSystem().level < newClass.minlevel) {
                p.sendMessage(AncientRPG.brand2 + "You need to be level " + newClass.minlevel + " to join this class");
                return false;
            }
        }
        if (!newClass.isWorldEnabled(p)) {
            return false;
        }
        if (newClass.preclass != null && !newClass.preclass.equals("") && !newClass.preclass.toLowerCase().equals(pd.getClassName().toLowerCase())) {
            return false;
        }
        return !(!(newClass.permissionNode == null || newClass.permissionNode.equalsIgnoreCase(
                "")) && !AncientRPG.hasPermissions(p, newClass.permissionNode));
    }

    public static void setStance(AncientRPGClass oldstance, AncientRPGClass newStance, AncientRPGClass rootclass, final Player p, Player sender) {
        PlayerData pd = PlayerData.getPlayerData(p.getName());
        if (newStance != null) {
            if (sender != p && AncientRPGExperience.isEnabled() && AncientRPGExperience.isWorldEnabled(p)) {
                if (pd.getXpSystem().level < newStance.minlevel) {
                    p.sendMessage(AncientRPG.brand2 + "You need to be level " + newStance.minlevel + " to join this stance");
                    return;
                }
            }
            if (!newStance.isWorldEnabled(p)) {
                p.sendMessage(AncientRPG.brand2 + "This stance cannot be used in this world");
                return;
            }
            if (sender == p && !(newStance.permissionNode == null || newStance.permissionNode.equalsIgnoreCase("")) && !AncientRPG.hasPermissions(sender, newStance.permissionNode)) {
                p.sendMessage(AncientRPG.brand2 + "You don't have permissions to set join stance");
                return;
            }
            if (sender == p && newStance.preclass != null && !newStance.preclass.equals("") && !oldstance.name.equalsIgnoreCase(newStance.preclass)) {
                p.sendMessage(AncientRPG.brand2 + "You need to be the stance " + newStance.preclass);
                return;
            }
            try {
                if (oldstance != null && oldstance.permGroup != null && !oldstance.permGroup.equals("")) {
                    if (AncientRPG.permissionHandler != null) {
                        AncientRPG.permissionHandler.playerRemoveGroup(p, oldstance.permGroup);
                    }
                    if (AncientRPGExperience.isEnabled()) {
                        pd.getClassLevels().put(oldstance.name.toLowerCase(), pd.getXpSystem().xp);
                    }
                }
            } catch (Exception ignored) {

            }
            pd.setStance(newStance.name);
            if (AncientRPGExperience.isEnabled()) {
                pd.getHpsystem().setHpRegen();
                pd.getHpsystem().setMinecraftHP();
            }
            // pd.hpsystem.maxhp = ct.getMaxHp();
            pd.setBindings(new HashMap<BindingData, String>());
            pd.getHpsystem().maxhp = newStance.hp;
            p.sendMessage(AncientRPG.brand2 + "Your stance is now " + newStance.name);
            try {
                if (newStance.permGroup != null && !newStance.permGroup.equals("") && AncientRPG.permissionHandler != null) {
                    AncientRPG.permissionHandler.playerAddGroup(p, newStance.permGroup);
                }
            } catch (Exception ignored) {

            }
            for (Map.Entry<BindingData, String> bind : rootclass.bindings.entrySet()) {
                pd.getBindings().put(bind.getKey(), bind.getValue());
            }
            for (Map.Entry<BindingData, String> bind : newStance.bindings.entrySet()) {
                pd.getBindings().put(bind.getKey(), bind.getValue());
            }
            AncientRPGClass.playersOnCd.put(p.getName(), System.currentTimeMillis());
            File f = new File(AncientRPG.plugin.getDataFolder() + File.separator + "Class" + File.separator + "changecds.dat");
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(AncientRPGClass.playersOnCd);
                oos.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        pd.getManasystem().setMaxMana();
    }
}
