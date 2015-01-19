package com.ancientshores.AncientRPG.Classes.Commands;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.API.AncientRPGClassChangeEvent;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;

public class ClassResetCommand {
    public static void resetCommand(Player p) {
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        AncientRPGClass oldClass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
        reset(p, oldClass, pd);
        p.sendMessage(AncientRPG.brand2 + "Successfully reset your class");
    }

    public static void reset(Player p, AncientRPGClass oldClass, PlayerData pd) {
    	AncientRPGClassChangeEvent classevent = new AncientRPGClassChangeEvent(p.getUniqueId(), oldClass, null);
        Bukkit.getPluginManager().callEvent(classevent);
        if (classevent.isCancelled())
            return;
        
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
        boolean defaultClassEnabledInNewWorld = false;
        
        for (String className : AncientRPGClass.classList.keySet())
			if (className.equalsIgnoreCase(AncientRPGClass.standardclassName))
				if (AncientRPGClass.classList.get(className).isWorldEnabled(p.getWorld())) {
					defaultClassEnabledInNewWorld = true;
					break;
				}
        
        String newClassName;
        if (defaultClassEnabledInNewWorld)
        	newClassName = AncientRPGClass.standardclassName;
        else
        	newClassName = "";
        	
        if (AncientRPGExperience.isEnabled()) {
        	pd.getClassLevels().put(oldClass.name.toLowerCase(), pd.getXpSystem().xp);
        	if (pd.getClassLevels().get(newClassName) == null)
        		pd.getClassLevels().put(newClassName, 0);
        		
        	pd.getXpSystem().xp = pd.getClassLevels().get(newClassName);
        		
        	pd.getXpSystem().addXP(0, false);
        }
        pd.setClassName(newClassName);
        pd.setStance("");
    }
}