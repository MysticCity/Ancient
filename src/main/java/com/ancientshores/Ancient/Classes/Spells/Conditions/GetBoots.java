package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetBoots extends IArgument {
    @ArgumentDescription(
            description = "Returns the id of the players boots",
            parameterdescription = {"player"}, returntype = ParameterType.Number, rparams = {ParameterType.Player})
    public GetBoots() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "getboots";
    }

    @SuppressWarnings("deprecation")
	@Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Entity[])) {
            return null;
        }
        Entity ent = ((Entity[]) obj[0])[0];
        if (((LivingEntity) ent).getEquipment().getBoots() == null) {
            return 0;
        }
        return ((LivingEntity) ent).getEquipment().getBoots().getTypeId();
    }
}