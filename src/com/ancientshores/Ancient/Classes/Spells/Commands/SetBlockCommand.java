package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.Location;
import org.bukkit.Material;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class SetBlockCommand extends ICommand {
    @CommandDescription(description = "<html>Sets the block at the location to the specified id</html>",
            argnames = {"location", "material"}, name = "SetBlock", parameters = {ParameterType.Location, ParameterType.Material})
    public SetBlockCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Material};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Location[] && ca.getParams().get(1) instanceof Material) {
                final Location loc[] = (Location[]) ca.getParams().get(0);
                final Material m = (Material) ca.getParams().get(1);
                if (loc != null && m != null) {
                    for (Location l : loc) {
                        if (l == null) {
                            continue;
                        }
                        l.getBlock().setType(m);
                    }
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}