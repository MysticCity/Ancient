package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class GetItemInHands extends IArgument {
    @ArgumentDescription(
            description = "Returns the amount of items of the specified id in the players inventory",
            parameterdescription = {"player"}, returntype = ParameterType.Player, rparams = {ParameterType.Player})
    public GetItemInHands() {
        this.pt = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "getiteminhands";
    }

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