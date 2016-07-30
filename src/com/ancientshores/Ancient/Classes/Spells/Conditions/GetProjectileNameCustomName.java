package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.apache.commons.lang.WordUtils;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Listeners.AncientPlayerListener;

public class GetProjectileNameCustomName extends IArgument {
	@ArgumentDescription(
			description = "Returns the name of the projectile with the custom set name, only usable in projectilehitevent",
			parameterdescription = {}, returntype = ParameterType.String, rparams = {})
	public GetProjectileNameCustomName() {
		this.returnType = ParameterType.String;
		this.requiredTypes = new ParameterType[]{};
		this.name = "getprojectilename:customname";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so) {
		if (so.mEvent instanceof ProjectileHitEvent) {
			ProjectileHitEvent pEvent = (ProjectileHitEvent) so.mEvent;
			String name = WordUtils.capitalizeFully(pEvent.getEntity().getType().toString().replaceAll("_", " ")).replaceAll(" ", "");
			
			// add custom name if there is one
			if (AncientPlayerListener.thrownProjectiles.containsKey(pEvent.getEntity())) 
				name += ":" + AncientPlayerListener.thrownProjectiles.get(pEvent.getEntity());
		   
			return name;
		}
		return null;
	}
}