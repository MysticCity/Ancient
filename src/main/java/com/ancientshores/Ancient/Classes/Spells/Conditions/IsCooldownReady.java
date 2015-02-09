package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.CooldownTimer;
import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class IsCooldownReady extends IArgument {
	@ArgumentDescription(
			description = "Returns true if the cooldown with the name is ready",
			parameterdescription = {"cooldownname"}, returntype = ParameterType.Boolean, rparams = {ParameterType.String})
	public IsCooldownReady() {
		this.returnType = ParameterType.Boolean;
		this.requiredTypes = new ParameterType[]{ParameterType.String};
		this.name = "iscooldownready";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so) {
		if (obj.length < 1 || !(obj[0] instanceof String))
			return null;

		String cooldownname = (String) obj[0];
		PlayerData pd = PlayerData.getPlayerData(so.buffcaster);
		for (CooldownTimer cd : pd.getCooldownTimer())
			if (cd.cooldownname.equals(cooldownname))
				return cd.ready;
		return true;
	}
}