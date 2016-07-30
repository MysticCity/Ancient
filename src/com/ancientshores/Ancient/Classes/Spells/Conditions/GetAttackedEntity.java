package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.event.entity.EntityDamageEvent;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.GlobalMethods;

public class GetAttackedEntity extends IArgument {
    @ArgumentDescription(
            description = "Returns the type of the attacked entity",
            parameterdescription = {}, returntype = ParameterType.Entity, rparams = {})
    public GetAttackedEntity() {
        this.returnType = ParameterType.Entity;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getattackedentity";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (so.mEvent instanceof EntityDamageEvent) {
            return GlobalMethods.getStringByEntity(((EntityDamageEvent) so.mEvent).getEntity());
        }
        return "";
    }
}