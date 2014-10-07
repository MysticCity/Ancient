package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.Location;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetProjectileHitLocation extends IArgument {
    @ArgumentDescription(
            description = "Returns the location the projectile hit, only usable in projectilehitevent",
            parameterdescription = {}, returntype = ParameterType.Location, rparams = {})
    public GetProjectileHitLocation() {
        this.returnType = ParameterType.Location;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getprojectilehitlocation";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (so.mEvent instanceof ProjectileHitEvent) {
            ProjectileHitEvent pEvent = (ProjectileHitEvent) so.mEvent;
            return new Location[]{pEvent.getEntity().getLocation()};
        }
        return null;
    }
}