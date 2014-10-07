package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetPlayerByUUID extends IArgument {
	@ArgumentDescription(
			description = "Finds an online player on the server with the specified uuid",
			parameterdescription = {"uuid"}, returntype = ParameterType.Player, rparams = {ParameterType.UUID})
	public GetPlayerByUUID() {
		this.returnType = ParameterType.Player;
		this.requiredTypes = new ParameterType[]{ParameterType.UUID};
		this.name = "getplayerbyuuid";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so) {
		if (!(obj[0] instanceof String)) {
			return null;
		}
		return new Player[]{Bukkit.getServer().getPlayer(UUID.fromString((String) obj[0]))};
	}
}