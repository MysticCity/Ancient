package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetItemInHands extends IArgument {
    @ArgumentDescription(
            description = "Returns the amount of items of the specified id in the players inventory",
            parameterdescription = {"player"}, returntype = ParameterType.Player, rparams = {ParameterType.Player})
    public GetItemInHands() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "getiteminhands";
    }

    @SuppressWarnings("deprecation")
	@Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Entity[])) {
            return null;
        }
        Entity ent = ((Entity[]) obj[0])[0];
        if (((LivingEntity) ent).getEquipment().getItemInHand() == null) {
            return 0;
        }
        return ((LivingEntity) ent).getEquipment().getItemInHand().getTypeId();
    }
}