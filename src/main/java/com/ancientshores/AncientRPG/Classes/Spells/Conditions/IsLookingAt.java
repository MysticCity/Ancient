package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 23.02.13
 * Time: 23:16
 */
public class IsLookingAt extends IArgument
{
	public IsLookingAt()
	{
		this.pt = ParameterType.Boolean;
		this.requiredTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Entity, ParameterType.Number};
		this.name = "islookingat";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if (obj.length == 3 && obj[1] instanceof Entity[] && ((Entity[]) obj[1]).length > 0 && obj[0] instanceof Entity[] && ((Entity[]) obj[0]).length > 0 && obj[2] instanceof Number)
		{
			Entity ent1 = ((Entity[]) obj[0])[0];
			Entity ent2 = ((Entity[]) obj[1])[0];
			int range = ((Number)obj[2]).intValue();
			if (ent1 != null && ent2 != null && ent1 instanceof LivingEntity && ent2 instanceof LivingEntity)
			{
				Entity ent = so.getNearestEntityInSight((LivingEntity)ent1, range);
				if(ent == ent2)
				{
					return true;
				}
			}
		}
		return false;
	}
}