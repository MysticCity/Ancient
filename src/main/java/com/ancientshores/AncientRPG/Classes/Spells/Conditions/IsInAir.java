package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class IsInAir extends IArgument
{
	@ArgumentDescription(
			description = "Returns true if the entity is in air, false otherwise",
			parameterdescription = {"entity"}, returntype = ParameterType.Boolean, rparams ={ParameterType.Entity})
	public IsInAir()
	{
		this.pt = ParameterType.Boolean;
		this.requiredTypes = new ParameterType[] { ParameterType.Entity };
		this.name = "isinair";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (obj.length == 1 && obj[0] instanceof Entity[] && ((Entity[]) obj[0]).length > 0)
		{
			Entity e = ((Entity[]) obj[0])[0];
			if (e != null && e instanceof LivingEntity)
			{
				return e.getLocation().getBlock().getY() == 0 || e.getLocation().getBlock().getRelative(0, -1, 0).getType() == Material.AIR || e.getLocation().getBlock().getY() == 0;
			}
		}
		return false;
	}
}
