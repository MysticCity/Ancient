package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class TeleportPlayerCommand extends ICommand {
    @CommandDescription(description = "<html>Teleports the target to the location</html>",
            argnames = {"entity", "location"}, name = "TeleportPlayer", parameters = {ParameterType.Entity, ParameterType.Location})

    public TeleportPlayerCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Location};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 2 && ca.getParams().get(0) instanceof Entity[] && ca.getParams().get(1) instanceof Location[]) {
            final Entity[] en = (Entity[]) ca.getParams().get(0);
            final Location[] loc = (Location[]) ca.getParams().get(1);
            for (Location l : loc) {
                if (l == null) {
                    continue;
                }
                for (Entity e : en) {
                    if (e == null) {
                        continue;
                    }
                    e.teleport(l);
                }
            }
            return true;
        }
        return false;
    }
}