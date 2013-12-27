package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.event.entity.EntityDamageEvent;

public class GetDamageCause extends IArgument {
    @ArgumentDescription(
            description = "Returns the name of the damage cause",
            parameterdescription = {}, returntype = ParameterType.String, rparams = {})
    public GetDamageCause() {
        this.pt = ParameterType.String;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getdamagecause";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (so.mEvent instanceof EntityDamageEvent) {
            EntityDamageEvent event = (EntityDamageEvent) so.mEvent;
            return event.getCause().toString().toLowerCase();
        }
        return null;
    }
}
