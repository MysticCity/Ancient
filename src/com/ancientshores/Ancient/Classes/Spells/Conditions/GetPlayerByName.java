package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetPlayerByName extends IArgument {
	@ArgumentDescription(
			description = "Finds an online player on the server with the specified name",
			parameterdescription = {"name"}, returntype = ParameterType.Player, rparams = {ParameterType.String})
	public GetPlayerByName() {
		this.returnType = ParameterType.Player;
		this.requiredTypes = new ParameterType[]{ParameterType.String};
		this.name = "getplayerbyname";
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object getArgument(Object obj[], SpellInformationObject so) {
		if (!(obj[0] instanceof String)) {
			return null;
		}
		return new Player[]{Bukkit.getServer().getPlayer((String) obj[0])};
	}
}