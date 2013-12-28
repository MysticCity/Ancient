package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Util.GlobalMethods;
import org.bukkit.event.entity.EntityDamageEvent;

public class GetAttackedEntity extends IArgument {
    @ArgumentDescription(
            description = "Returns the type of the attacked entity",
            parameterdescription = {}, returntype = ParameterType.Entity, rparams = {})
    public GetAttackedEntity() {
        this.pt = ParameterType.Entity;
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