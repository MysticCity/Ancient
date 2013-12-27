package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class EntitiesInRage extends IArgument
{
	@ArgumentDescription(
			description = "returns the amount of entities in the range from the location.",
			parameterdescription = {"location", "range"}, returntype = ParameterType.Number, rparams ={ParameterType.Location, ParameterType.Number})
	public EntitiesInRage()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number };
		this.name = "entitiesinrange";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (obj.length == 2 && obj[0] instanceof Location[] && obj[1] instanceof Number)
		{
			Location l = ((Location[]) obj[0])[0];
			int range = (int) ((Number) obj[1]).doubleValue();
			List<Entity> ents = l.getWorld().getEntities();
			int amount = 0;
			for(Entity e : ents)
			{
				if(e instanceof LivingEntity && e.getLocation().distance(l) < range)
				{
					amount++;
				}
			}
			return amount;
		}
		return 0;
	}
}
