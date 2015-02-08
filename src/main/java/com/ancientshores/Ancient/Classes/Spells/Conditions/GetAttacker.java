package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.GlobalMethods;

public class GetAttacker extends IArgument {
    @ArgumentDescription(
            description = "Returns the type of the attacker",
            parameterdescription = {}, returntype = ParameterType.String, rparams = {})
    public GetAttacker() {
        this.returnType = ParameterType.String;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getattacker";
    }

    @Override
    public Object getArgument(Object mPlayer[], SpellInformationObject so) {
        if (so.mEvent instanceof EntityDamageByEntityEvent) {
            return GlobalMethods.getStringByEntity(((EntityDamageByEntityEvent) so.mEvent).getDamager());
        }
        return "";
    }
}