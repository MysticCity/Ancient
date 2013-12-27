package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.event.entity.EntityDamageEvent;

public class GetDamage extends IArgument {
    @ArgumentDescription(
            description = "returns the damage of the damage event",
            parameterdescription = {}, returntype = ParameterType.Number, rparams = {})
    public GetDamage() {
        this.pt = ParameterType.Number;
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
