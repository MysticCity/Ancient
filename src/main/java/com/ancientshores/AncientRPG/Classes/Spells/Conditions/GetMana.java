package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

public class GetMana extends IArgument
{
	@ArgumentDescription(
			description = "Returns the current amount of mana of the player",
			parameterdescription = {"player"}, returntype = ParameterType.Number, rparams ={ParameterType.Player})
	public GetMana()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[]{ParameterType.Player};
		this.name = "getmana";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (!(obj[0] instanceof Player[]))
			return 0;
		Player e = ((Player[]) obj[0])[0];
		return PlayerData.getPlayerData(e.getName()).getManasystem().getCurmana();
	}
}
