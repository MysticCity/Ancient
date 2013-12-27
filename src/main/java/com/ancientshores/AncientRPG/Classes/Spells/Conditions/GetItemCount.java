package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetItemCount extends IArgument
{
	@ArgumentDescription(
			description = "Returns the amount of items of the specified type",
			parameterdescription = {"player", "material"}, returntype = ParameterType.Number , rparams ={ParameterType.Player, ParameterType.Material})
	public GetItemCount()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[] { ParameterType.Player, ParameterType.Material };
		this.name = "getitemcount";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		Material m = null;
		if (obj.length < 2)
			return null;
		if (!(obj[0] instanceof Player[]))
			return null;
		if (obj[1] instanceof Material)
		{
			m = (Material) obj[1];
		}
		if (obj[1] instanceof Number)
		{
			m = Material.getMaterial((int) ((Number) obj[1]).doubleValue());
		}
		Player mPlayer = ((Player[]) obj[0])[0];
		int amount = 0;
		for (ItemStack is : mPlayer.getInventory().getContents())
		{
			if (is != null && is.getType() == m)
			{
				amount += is.getAmount();
			}
		}
		return amount;
	}
}
