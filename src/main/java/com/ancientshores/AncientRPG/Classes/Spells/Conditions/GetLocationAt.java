package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class GetLocationAt extends IArgument
{
	@ArgumentDescription(
			description = "Returns the location in the specified world with the specified coordinates",
			parameterdescription = {"worldname", "xcoord", "ycoord", "zcoord"}, returntype = ParameterType.Location, rparams ={ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public GetLocationAt()
	{
		this.pt = ParameterType.Location;
		this.requiredTypes = new ParameterType[] { ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number };
		this.name = "getlocationat";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (obj.length == 4 && obj[0] instanceof String && obj[1] instanceof Number && obj[2] instanceof Number && obj[3] instanceof Number)
		{
			String wname = (String) obj[0];
			double x = ((Number) obj[1]).doubleValue();
			double y = ((Number) obj[2]).doubleValue();
			double z = ((Number) obj[3]).doubleValue();
			for (World w : Bukkit.getWorlds())
			{
				if (w.getName().equalsIgnoreCase(wname))
				{
					Location l = new Location(w, x, y, z);
					return new Location[] { l };
				}
			}
		}
		return null;
	}
}
