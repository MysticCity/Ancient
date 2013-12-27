package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.event.entity.ProjectileHitEvent;

public class GetProjectileName extends IArgument {
    @ArgumentDescription(
            description = "Returns the name of the projectile, only usable in projectilehitevent",
            parameterdescription = {}, returntype = ParameterType.String, rparams = {})
    public GetProjectileName() {
        this.pt = ParameterType.String;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getprojectilename";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (so.mEvent instanceof ProjectileHitEvent) {
            ProjectileHitEvent pEvent = (ProjectileHitEvent) so.mEvent;
            return pEvent.getEntity().getType().getName();
        }
        return null;
    }
}
