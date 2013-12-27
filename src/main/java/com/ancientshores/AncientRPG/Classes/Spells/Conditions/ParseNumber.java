package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class ParseNumber extends IArgument
{
	@ArgumentDescription(
			description = "Parses a string and tries to convert it into a number",
			parameterdescription = {"numberasstring"}, returntype = ParameterType.Number, rparams ={ParameterType.String})
	public ParseNumber()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[] { ParameterType.String };
		this.name = "parsenumber";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (!(obj[0] instanceof String))
			return 0;
		String s = (String) obj[0];
		try
		{
			return Double.parseDouble(s);
		}
		catch(Exception ignored)
		{
		}
		return -1;
	}
}
