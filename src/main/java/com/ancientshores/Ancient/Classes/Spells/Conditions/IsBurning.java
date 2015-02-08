package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class IsBurning extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the entity is buring, false otherwise",
            parameterdescription = {"entity"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Entity})
    public IsBurning() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Entity};
        this.name = "isburning";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 1 && obj[0] instanceof Entity[] && ((Entity[]) obj[0]).length > 0) {
            Entity e = ((Entity[]) obj[0])[0];
            if (e != null && e instanceof LivingEntity) {
                return e.getFireTicks() > 0;
            }
        }
        return false;
    }
}