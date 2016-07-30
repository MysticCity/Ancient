package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.apache.commons.lang.WordUtils;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetProjectileName extends IArgument {
    @ArgumentDescription(
            description = "Returns the name of the projectile, only usable in projectilehitevent",
            parameterdescription = {}, returntype = ParameterType.String, rparams = {})
    public GetProjectileName() {
        this.returnType = ParameterType.String;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getprojectilename";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (so.mEvent instanceof ProjectileHitEvent) {
            ProjectileHitEvent pEvent = (ProjectileHitEvent) so.mEvent;
            return WordUtils.capitalizeFully(pEvent.getEntity().getType().toString().replaceAll("_", " ")).replaceAll(" ", "");
        }
        return null;
    }
}