package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayersInRange extends IArgument
{
	@ArgumentDescription(
			description = "Returns the amount of players in range",
			parameterdescription = {"location", "range"}, returntype = ParameterType.Number, rparams ={ParameterType.Location, ParameterType.Number})
	public PlayersInRange()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number };
		this.name = "playersinrange";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (obj.length == 2 && obj[0] instanceof Location[] && obj[1] instanceof Number)
		{
			if(((Location[]) obj[0]).length <= 0 || ((Location[]) obj[0])[0] == null)
				return 0;
			Location l = ((Location[]) obj[0])[0];
			int range = (int) ((Number) obj[1]).doubleValue();
			List<Entity> ents = l.getWorld().getEntities();
			int amount = 0;
			for(Entity e : ents)
			{
				if(e instanceof Player && e.getLocation().distance(l) < range)
				{
					amount++;
				}
			}
			return amount;
		}
		return 0;
	}
}
