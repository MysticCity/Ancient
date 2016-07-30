package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetRandomBetween extends IArgument {
	@ArgumentDescription(
			description = "Returns a random number between 2 numbers",
			parameterdescription = {"minimum", "maximum"}, returntype = ParameterType.Number, rparams = {ParameterType.Number, ParameterType.Number})
	public GetRandomBetween() {
		this.returnType = ParameterType.Number;
		this.requiredTypes = new ParameterType[]{ParameterType.Number, ParameterType.Number};
		this.name = "getrandombetween";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so) {
		if (obj.length == 2 && obj[0] instanceof Number && obj[1] instanceof Number) {
			int min = ((Number) obj[0]).intValue();
			int max = ((Number) obj[1]).intValue();
			int delta = max - min;
			return (int) ((Math.random() * delta) + min);
		}
		return null;
	}
}