package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Util.GlobalMethods;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GetAttacker extends IArgument {
    @ArgumentDescription(
            description = "Returns the type of the attacker",
            parameterdescription = {}, returntype = ParameterType.String, rparams = {})
    public GetAttacker() {
        this.pt = ParameterType.String;
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