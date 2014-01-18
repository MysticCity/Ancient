package com.ancientshores.AncientRPG.Classes.Commands;

import com.ancientshores.AncientRPG.API.AncientRPGClassChangeEvent;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class ClassResetCommand {
    public static void resetCommand(Player p) {
        PlayerData pd = PlayerData.getPlayerData(p.getName());
        AncientRPGClass oldClass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
        reset(p, oldClass, pd);
        p.sendMessage(AncientRPG.brand2 + "Successfully reset your class");
    }

    public static void reset(Player p, AncientRPGClass oldClass, PlayerData pd) {
        AncientRPGClassChangeEvent classevent = new AncientRPGClassChangeEvent(p, oldClass, null);
        Bukkit.getPluginManager().callEvent(classevent);
        if (classevent.isCancelled()) {
            return;
        }
        if (oldClass != null && oldClass.permGroup != null && !oldClass.permGroup.equals("")) {
            if (AncientRPG.permissionHandler != null) {
                AncientRPG.permissionHandler.playerRemoveGroup(p, oldClass.permGroup);
                for (Map.Entry<String, AncientRPGClass> entry : oldClass.stances.entrySet()) {
                    AncientRPG.permissionHandler.playerRemoveGroup(p, entry.getValue().permGroup);
                }
            }
            if (AncientRPGExperience.isEnabled()) {
                pd.getClassLevels().put(oldClass.name, pd.getXpSystem().xp);
            }
        }
        if (AncientRPGExperience.isEnabled()) {
            if (pd.getClassLevels().get(AncientRPGClass.standardclassName) == null) {
                pd.getClassLevels().put(AncientRPGClass.standardclassName, 0);
            }
            pd.getXpSystem().xp = pd.getClassLevels().get(AncientRPGClass.standardclassName);
            pd.getXpSystem().addXP(0, false);
        }
        pd.setClassName(AncientRPGClass.standardclassName);
        pd.setStance("");
    }
}