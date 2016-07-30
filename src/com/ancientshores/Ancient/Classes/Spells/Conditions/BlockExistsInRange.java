package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class BlockExistsInRange extends IArgument {
    @ArgumentDescription(
            description = "if the specified block exists in the range returns true",
            parameterdescription = {"location", "material", "range"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Location, ParameterType.Material, ParameterType.Number})
    public BlockExistsInRange() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Material, ParameterType.Number};
        this.name = "blockexistsinrange";
    }

    @SuppressWarnings("deprecation")
	@Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 3 && obj[0] instanceof Location[] && obj[1] instanceof Number && obj[2] instanceof Number) {
            Location l = ((Location[]) obj[0])[0];
            Material mat = Material.getMaterial(((Number) obj[1]).intValue());
            int range = ((Number) obj[2]).intValue();
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    for (int z = -range; z <= range; z++) {
                        Location ln = l.clone().add(new Vector(x, y, z));
                        if (ln.getBlock().getType() == mat) {
                            if (l.distance(ln) <= range) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}