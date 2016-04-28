package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.Location;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class TeleportCommand extends ICommand {
    @CommandDescription(description = "<html>Teleports the player to the location</html>",
            argnames = {"location"}, name = "Teleport", parameters = {ParameterType.Location})

    public TeleportCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Location};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Location[]) {
                final Location[] loc = (Location[]) ca.getParams().get(0);
                for (Location l : loc) {
                    if (l == null) {
                        continue;
                    }
                    Location lo = ca.getCaster().getLocation();
                    lo.setX(l.getX());
                    lo.setY(l.getY());
                    lo.setZ(l.getZ());
                    ca.getCaster().teleport(lo);
                }
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}