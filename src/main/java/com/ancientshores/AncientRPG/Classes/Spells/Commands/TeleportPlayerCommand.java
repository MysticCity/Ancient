package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class TeleportPlayerCommand extends ICommand {
    @CommandDescription(description = "<html>Teleports the target to the location</html>",
            argnames = {"entity", "location"}, name = "TeleportPlayer", parameters = {ParameterType.Entity, ParameterType.Location})

    public TeleportPlayerCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.Location};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.params.size() == 2 && ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Location[]) {
            final Entity[] en = (Entity[]) ca.params.get(0);
            final Location[] loc = (Location[]) ca.params.get(1);
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