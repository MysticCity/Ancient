package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

public class GetLevel extends IArgument
{
	@ArgumentDescription(
			description = "Returns the level of the player",
			parameterdescription = {"player"}, returntype = ParameterType.Number, rparams ={ParameterType.Player})
	public GetLevel()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[]{ParameterType.Player};
		this.name = "getlevel";
	}
	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if(!(obj[0] instanceof Player[]))
				return null;
		Player mPlayer = ((Player[]) obj[0])[0];
		if(AncientRPGExperience.isEnabled() && AncientRPGExperience.isWorldEnabled(mPlayer))
		{
			PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
			return pd.getXpSystem().level;
		}
		return 0;
	}
}
