package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetRandom extends IArgument
{
	@ArgumentDescription(
			description = "Returns a random number between 0 and 100 (both inclusive)",
			parameterdescription = {}, returntype = ParameterType.Number, rparams ={})
	public GetRandom()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[]{};
		this.name = "getrandom";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		return Math.abs((int) Math.round(Math.random() * 100));
	}
}