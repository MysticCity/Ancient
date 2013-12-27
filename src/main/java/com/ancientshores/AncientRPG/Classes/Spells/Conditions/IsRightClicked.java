package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class IsRightClicked extends IArgument
{
	@ArgumentDescription(
			description = "Returns true if the interactevent is right clicked",
			parameterdescription = {}, returntype = ParameterType.Boolean, rparams ={})
	public IsRightClicked()
	{
		this.pt = ParameterType.String;
		this.requiredTypes = new ParameterType[] {  };
		this.name = "isrightclicked";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (so.mEvent instanceof PlayerInteractEvent)
		{
			PlayerInteractEvent pie = (PlayerInteractEvent) so.mEvent;
			return pie.getAction() == Action.RIGHT_CLICK_AIR || pie.getAction() == Action.RIGHT_CLICK_BLOCK;
		}
		return "";
	}
}
