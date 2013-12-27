package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class GetItemAmountInItemSlot extends IArgument
{
	@ArgumentDescription(
			description = "Returns the amount of items in the specified item slot",
			parameterdescription = {"player", "slot"}, returntype = ParameterType.Number, rparams ={ParameterType.Player, ParameterType.Number})
	public GetItemAmountInItemSlot()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number };
		this.name = "getitemamountinitemslot";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (obj.length == 2 && obj[0] instanceof Player[] && ((Player[])obj[0])[0] != null && obj[1] instanceof Number)
		{
			Player p = ((Player[]) obj[0])[0];
			int slot = (int) ((Number) obj[1]).doubleValue();
			if(p.getInventory().getItem(slot) == null)
			{
				return 0;
			}
			else
			{
				return p.getInventory().getItem(slot).getAmount();
			}
		}
		return null;
	}
}
