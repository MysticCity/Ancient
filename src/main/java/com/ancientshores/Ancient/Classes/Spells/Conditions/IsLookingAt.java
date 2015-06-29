package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class IsLookingAt extends IArgument {
    public IsLookingAt() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Entity, ParameterType.Number};
        this.name = "islookingat";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 3 && obj[1] instanceof Entity[] && ((Entity[]) obj[1]).length > 0 && obj[0] instanceof Entity[] && ((Entity[]) obj[0]).length > 0 && obj[2] instanceof Number) {
            Entity ent1 = ((Entity[]) obj[0])[0];
            Entity ent2 = ((Entity[]) obj[1])[0];
            int range = ((Number) obj[2]).intValue();
            if (ent1 != null && ent2 != null && ent1 instanceof LivingEntity && ent2 instanceof LivingEntity) {
                if (so.getNearestEntityInSight((LivingEntity) ent1, range).getUniqueId().compareTo(ent2.getUniqueId()) == 0) {
                	return true;
                }
//            	Entity ent = null;
//                for (World w : Bukkit.getWorlds()) {
//        			for (Entity e : w.getEntities()) {
//        				if (e.getUniqueId().compareTo(so.getNearestEntityInSight((LivingEntity) ent1, range).getUniqueId()) == 0) {
//        					ent = e;
//        				}
//        			}
//        		}
//                if (ent == ent2) {
//                    return true;
//                }
            }
        }
        return false;
    }
}