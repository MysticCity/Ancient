package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.PotionEffectCommand;

public class HasPotionEffect extends IArgument
{
	@ArgumentDescription(
			description = "Returns if the entity has the specified potioneffect",
			parameterdescription = {"entity", "potioneffecttype"}, returntype = ParameterType.Boolean, rparams ={ParameterType.Entity, ParameterType.String})
	public HasPotionEffect()
	{
		this.pt = ParameterType.Boolean;
		this.requiredTypes = new ParameterType[] { ParameterType.Entity, ParameterType.String };
		this.name = "haspotioneffect";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (obj.length != 2 || !(obj[0] instanceof Entity[]) || !(obj[1] instanceof String))
		{
			return false;
		}
		Entity e = ((Entity[]) obj[0])[0];
		String s = (String) obj[1];
		PotionEffectType pet = PotionEffectCommand.getTypeByName(s);
		if (e != null && e instanceof LivingEntity && pet != null)
		{
			LivingEntity le = (LivingEntity) e;
			return le.hasPotionEffect(pet);
		}
		return false;
	}
}
