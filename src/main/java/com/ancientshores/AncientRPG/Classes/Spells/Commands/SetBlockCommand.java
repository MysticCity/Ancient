package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;
import org.bukkit.Material;

public class SetBlockCommand extends ICommand {
    @CommandDescription(description = "<html>Sets the block at the location to the specified id</html>",
            argnames = {"location", "material"}, name = "SetBlock", parameters = {ParameterType.Location, ParameterType.Material})
    public SetBlockCommand() {
        ParameterType[] buffer = {ParameterType.Location, ParameterType.Material};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {

        try {
            if (ca.params.get(0) instanceof Location[] && ca.params.get(1) instanceof Material) {
                final Location loc[] = (Location[]) ca.params.get(0);
                final Material m = (Material) ca.params.get(1);
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
