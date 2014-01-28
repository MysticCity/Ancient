package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class GetHealth extends IArgument {
    @ArgumentDescription(
            description = "Returns the health of the player",
            parameterdescription = {"entity"}, returntype = ParameterType.Number, rparams = {ParameterType.Entity})
    public GetHealth() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Entity};
        this.name = "gethealth";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Entity[])) {
            return 0;
        }
        LivingEntity e = (LivingEntity) ((Entity[]) obj[0])[0];
        return e.getHealth();
    }
}