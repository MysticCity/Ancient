package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class IsInAir extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the entity is in air, false otherwise",
            parameterdescription = {"entity"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Entity})
    public IsInAir() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Entity};
        this.name = "isinair";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 1 && obj[0] instanceof Entity[] && ((Entity[]) obj[0]).length > 0) {
            Entity e = ((Entity[]) obj[0])[0];
            if (e != null && e instanceof LivingEntity) {
                return e.getLocation().getBlock().getY() == 0 || e.getLocation().getBlock().getRelative(0, -1, 0).getType() == Material.AIR || e.getLocation().getBlock().getY() == 0;
            }
        }
        return false;
    }
}