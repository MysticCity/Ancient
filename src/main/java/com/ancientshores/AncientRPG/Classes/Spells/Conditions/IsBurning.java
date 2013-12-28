package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class IsBurning extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the entity is buring, false otherwise",
            parameterdescription = {"entity"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Entity})
    public IsBurning() {
        this.pt = ParameterType.Boolean;
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