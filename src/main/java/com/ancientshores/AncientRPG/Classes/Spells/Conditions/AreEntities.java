package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class AreEntities extends IArgument
{
	@ArgumentDescription(
			description = "Checks a collection of locations if all of them are entities.",
			parameterdescription = {"collection"}, returntype = ParameterType.Boolean, rparams ={ParameterType.Location})
	public AreEntities()
	{
		this.pt = ParameterType.Boolean;
		this.requiredTypes = new ParameterType[] { ParameterType.Location };
		this.name = "areentities";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (!(obj[0] instanceof Object[]))
			return 0;
		Object[] objs = (Object[]) obj[0];
		if (objs.length == 0)
			return false;
		for (Object o : objs)
		{
			if (!(o instanceof Player) && !(o instanceof Entity))
			{
				return false;
			}
		}
		return true;
	}
}
