package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.HP.DamageConverter;

public class GetMaxHealth extends IArgument {
	@ArgumentDescription(
			description = "Returns the maximum health of the given entity",
			parameterdescription = {"entity"}, returntype = ParameterType.Number, rparams = {ParameterType.Entity})
	public GetMaxHealth() {
		this.returnType = ParameterType.Number;
		this.requiredTypes = new ParameterType[]{ParameterType.Entity};
		this.name = "getmaxhealth";
	}


	@Override
	public Object getArgument(Object obj[], SpellInformationObject so) {
		if (!(obj[0] instanceof Entity[])) {
			return 0;
		}
		LivingEntity e = (LivingEntity) ((Entity[]) obj[0])[0];
		if (e instanceof Player) {
			Player p = (Player) e;
			PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
			if (DamageConverter.isEnabledInWorld(p.getWorld())) return pd.getHpsystem().getMaxHP();
			
		}
		return e.getMaxHealth();
	}
}