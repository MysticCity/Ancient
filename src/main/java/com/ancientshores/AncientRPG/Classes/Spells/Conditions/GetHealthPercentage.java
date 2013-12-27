package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.HP.CreatureHp;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class GetHealthPercentage extends IArgument
{
	@ArgumentDescription(
			description = "Returns the health percentage of the entity",
			parameterdescription = {"entity"}, returntype = ParameterType.Number, rparams ={ParameterType.Entity})
	public GetHealthPercentage()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[] { ParameterType.Entity };
		this.name = "gethealthpercentage";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (!(obj[0] instanceof Entity[]))
			return 0;
		LivingEntity e = (LivingEntity) ((Entity[]) obj[0])[0];
		return Math.round(((float) e.getHealth() / (float) e.getMaxHealth()) * 100);
	}
}