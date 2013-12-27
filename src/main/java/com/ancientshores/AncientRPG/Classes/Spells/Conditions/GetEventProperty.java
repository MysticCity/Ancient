package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class GetEventProperty extends IArgument
{
	@ArgumentDescription(
			description = "Returns the class object of the event with the specified name",
			parameterdescription = {"objectname"}, returntype = ParameterType.Void, rparams ={ParameterType.String})
	public GetEventProperty()
	{
		this.pt = ParameterType.Void;
		this.requiredTypes = new ParameterType[] { ParameterType.String };
		this.name = "geteventproperty";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (obj.length == 1 && obj[0] instanceof String)
		{
			String property = (String) obj[0];
			if (so.mEvent != null)
			{
				Object o = so.mEvent;
				for (Field field : o.getClass().getDeclaredFields())
				{
					if (field.getName().equalsIgnoreCase(property))
					{
						field.setAccessible(true);
						try
						{
							Object returnVal = field.get(o);
							return returnVal;
						} catch (IllegalArgumentException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return "";
	}

	public Object AutoCast(Object o)
	{
		if (o instanceof Player)
		{
			return new Player[] { (Player) o };
		}
		if (o instanceof Entity)
		{
			return new Entity[] { (Entity) o };
		}
		if (o instanceof Location)
		{
			return new Location[] { (Location) o };
		}
		return o;
	}
}
