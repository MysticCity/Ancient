package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetItemInItemSlot extends IArgument {
	@ArgumentDescription(
			description = "Returns the item type of the item in the specified slot",
			parameterdescription = {"player", "slot"}, returntype = ParameterType.Number, rparams = {ParameterType.Player, ParameterType.Number})
	public GetItemInItemSlot() {
		this.returnType = ParameterType.Number;
		this.requiredTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
		this.name = "getiteminitemslot";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so) {
		if (obj.length == 2 && obj[0] instanceof Player[] && ((Player[]) obj[0])[0] != null && obj[1] instanceof Number) {
			Player p = ((Player[]) obj[0])[0];
			int slot = (int) ((Number) obj[1]).doubleValue();
			if (p.getInventory().getItem(slot) == null) {
				return 0;
			} else {
				return p.getInventory().getItem(slot).getType();
			}
		}
		return null;
	}
}