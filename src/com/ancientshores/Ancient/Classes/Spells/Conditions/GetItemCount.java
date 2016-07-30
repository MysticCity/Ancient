package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetItemCount extends IArgument {
	@ArgumentDescription(
			description = "Returns the amount of items of the specified type the player has in his inventory",
			parameterdescription = {"player", "material"}, returntype = ParameterType.Number, rparams = {ParameterType.Player, ParameterType.Material})
	public GetItemCount() {
		this.returnType = ParameterType.Number;
		this.requiredTypes = new ParameterType[]{ParameterType.Player, ParameterType.Material};
		this.name = "getitemcount";
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object getArgument(Object obj[], SpellInformationObject so) {
		Material m = null;
		if (obj.length < 2) {
			return null;
		}
		if (!(obj[0] instanceof Player[])) {
			return null;
		}
		try {
			int id = ((Double) obj[1]).intValue();
			m = Material.getMaterial(id);
		}
		catch (Exception ex) {
			m = Material.getMaterial((String) obj[1]);
		}
		if (m == null) return -1;
		
		Player p = ((Player[]) obj[0])[0];
		int amount = 0;
		for (ItemStack is : p.getInventory().all(m).values()) {
				amount += is.getAmount();
		}
		return amount;
	}
}