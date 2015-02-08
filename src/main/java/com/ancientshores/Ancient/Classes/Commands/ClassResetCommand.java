package com.ancientshores.Ancient.Classes.Commands;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.API.AncientClassChangeEvent;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Experience.AncientExperience;

public class ClassResetCommand {
    public static void resetCommand(Player p) {
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        AncientClass oldClass = AncientClass.classList.get(pd.getClassName().toLowerCase());
        reset(p, oldClass, pd);
        p.sendMessage(Ancient.brand2 + "Successfully reset your class");
    }

    public static void reset(Player p, AncientClass oldClass, PlayerData pd) {
    	AncientClassChangeEvent classevent = new AncientClassChangeEvent(p.getUniqueId(), oldClass, null);
        Bukkit.getPluginManager().callEvent(classevent);
        if (classevent.isCancelled())
            return;
        
        if (oldClass != null && oldClass.permGroup != null && !oldClass.permGroup.equals("")) {
        	if (Ancient.permissionHandler != null) {
                Ancient.permissionHandler.playerRemoveGroup(p, oldClass.permGroup);
                for (Map.Entry<String, AncientClass> entry : oldClass.stances.entrySet()) {
                    Ancient.permissionHandler.playerRemoveGroup(p, entry.getValue().permGroup);
                }
            }
            if (AncientExperience.isEnabled()) {
                pd.getClassLevels().put(oldClass.name, pd.getXpSystem().xp);
            }
        }
        boolean defaultClassEnabledInNewWorld = false;
        
        for (String className : AncientClass.classList.keySet())
			if (className.equalsIgnoreCase(AncientClass.standardclassName))
				if (AncientClass.classList.get(className).isWorldEnabled(p.getWorld())) {
					defaultClassEnabledInNewWorld = true;
					break;
				}
        
        String newClassName;
        if (defaultClassEnabledInNewWorld)
        	newClassName = AncientClass.standardclassName;
        else
        	newClassName = "";
        	
        if (AncientExperience.isEnabled()) {
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