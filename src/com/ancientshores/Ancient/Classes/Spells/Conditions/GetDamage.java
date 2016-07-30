package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.event.entity.EntityDamageEvent;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetDamage extends IArgument {
    @ArgumentDescription(
            description = "returns the damage of the damage event",
            parameterdescription = {}, returntype = ParameterType.Number, rparams = {})
    public GetDamage() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getdamage";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if ((so.mEvent instanceof EntityDamageEvent)) {
            EntityDamageEvent event = (EntityDamageEvent) so.mEvent;
            return event.getDamage();
        }
        return null;
    }
}