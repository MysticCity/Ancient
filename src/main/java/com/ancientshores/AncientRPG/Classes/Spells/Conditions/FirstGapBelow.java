package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class FirstGapBelow extends IArgument {
    @ArgumentDescription(
            description = "Returns the first gap below the specified location with atleast the amount of free blocks",
            parameterdescription = {"location", "free blocks"}, returntype = ParameterType.Location, rparams = {ParameterType.Location, ParameterType.Number})
    public FirstGapBelow() {
        this.returnType = ParameterType.Location;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number};
        this.name = "firstgapbelow";
    }

    @SuppressWarnings("deprecation")
	@Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 2 && obj[0] instanceof Location[] && obj[1] instanceof Number) {
            Location l = null;
            int freespace = ((Number) obj[1]).intValue();
            for (Location l1 : ((Location[]) obj[0])) {
                if (l1 != null) {
                    l = l1;
                    break;
                }
            }
            try {
                if (l != null) {
                    Block b = l.getBlock();
                    outerloop:
                    while (true) {
                        if (b.getLocation().getBlockY() > 256) {
                            return null;
                        }
                        b = b.getRelative(BlockFace.DOWN);
                        if (b.getTypeId() != 0) {
                            continue;
                        }
                        Block b1 = b.getRelative(BlockFace.SELF);
                        for (int i = 0; i < freespace - 1; i++) {
                            b1 = b1.getRelative(BlockFace.DOWN);
                            if (b1.getTypeId() != 0) {
                                continue outerloop;
                            }
                        }
                        break;
                    }
                    return new Location[]{b.getLocation()};
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}