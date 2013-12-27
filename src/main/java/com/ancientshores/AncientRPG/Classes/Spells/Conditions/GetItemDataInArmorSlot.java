package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class GetItemDataInArmorSlot extends IArgument {
    @ArgumentDescription(
            description = "Returns the data of the item in the specified armor slot",
            parameterdescription = {"player", "slot"}, returntype = ParameterType.Number, rparams = {ParameterType.Player, ParameterType.Number})
    public GetItemDataInArmorSlot() {
        this.pt = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
        this.name = "getitemdatainarmorslot";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 2 && obj[0] instanceof Entity[] && obj[1] instanceof Number) {
            final Entity[] ents = (Entity[]) obj[0];
            int slot = ((Number) obj[1]).intValue();
            if (ents.length < 0 || ents[0] == null) {
                return null;
            }
            LivingEntity ent = (LivingEntity) ents[0];
            switch (slot) {
                case 0:
                    return (int) ent.getEquipment().getHelmet().getData().getData();
                case 1:
                    return (int) ent.getEquipment().getChestplate().getData().getData();
                case 2:
                    return (int) ent.getEquipment().getLeggings().getData().getData();
                case 3:
                    return (int) ent.getEquipment().getBoots().getData().getData();
            }
        }
        return null;
    }
}
