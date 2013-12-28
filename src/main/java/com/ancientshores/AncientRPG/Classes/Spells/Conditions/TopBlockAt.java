package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class TopBlockAt extends IArgument {
    @ArgumentDescription(
            description = "Returns the top block at the specified location",
            parameterdescription = {"location"}, returntype = ParameterType.Location, rparams = {ParameterType.Location})
    public TopBlockAt() {
        this.pt = ParameterType.Location;
        this.requiredTypes = new ParameterType[]{ParameterType.Location};
        this.name = "topblockat";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 1 && obj[0] instanceof Location[]) {
            Location l = null;
            for (Location l1 : ((Location[]) obj[0])) {
                if (l1 != null) {
                    l = l1;
                    break;
                }
            }
            try {
                if (l != null) {
                    l = l.add(0, 255 - l.getY(), 0);
                    Block b = l.getBlock();
                    while (b.getTypeId() == 0) {
                        b = b.getRelative(BlockFace.DOWN);
                        if (b.getY() <= 0) {
                            return null;
                        }
                    }
                    return new Location[]{b.getLocation()};
                }
            } catch (Exception ignored) {

            }
        }
        return null;
    }
}