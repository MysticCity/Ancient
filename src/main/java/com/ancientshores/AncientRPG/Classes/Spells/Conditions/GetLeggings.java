package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class GetLeggings extends IArgument
{
	@ArgumentDescription(
			description = "Returns the id of the players leggings",
			parameterdescription = {"player"}, returntype = ParameterType.Number, rparams ={ParameterType.Player})
	public GetLeggings()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[]{ParameterType.Player};
		this.name = "getleggings";
	}
	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if(!(obj[0] instanceof Entity[]))
			return null;
		Entity ent = ((Entity[]) obj[0])[0];
		if(((LivingEntity) ent).getEquipment().getLeggings() == null)
			return 0;
		return ((LivingEntity) ent).getEquipment().getLeggings().getTypeId();
	}
}
