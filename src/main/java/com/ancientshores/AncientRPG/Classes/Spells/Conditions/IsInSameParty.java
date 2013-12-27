package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class IsInSameParty extends IArgument
{
	@ArgumentDescription(
			description = "Returns true if the 2 players are in the same party, false otherwise",
			parameterdescription = {"player1", "player2"}, returntype = ParameterType.Boolean, rparams ={ParameterType.Player, ParameterType.Player})
	public IsInSameParty()
	{
		this.pt = ParameterType.Boolean;
		this.requiredTypes = new ParameterType[] { ParameterType.Player, ParameterType.Player };
		this.name = "isinsameparty";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (!(obj[0] instanceof Player[]) || !(obj[1] instanceof Player[]))
		{
			return null;
		}
		if (((Player[]) obj[0]).length > 0 && ((Player[]) obj[1]).length > 0 && ((Player[]) obj[0])[0] != null && ((Player[]) obj[1])[0] != null)
		{
			Player p1 = ((Player[]) obj[0])[0];
			Player p2 = ((Player[]) obj[1])[0];
			AncientRPGParty party1 = AncientRPGParty.getPlayersParty(p1);
			AncientRPGParty party2 = AncientRPGParty.getPlayersParty(p2);
			if (party1 != null && party1 == party2)
			{
				return true;
			}
		}
		return false;
	}
}
