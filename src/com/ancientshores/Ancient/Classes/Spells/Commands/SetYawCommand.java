package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class SetYawCommand extends ICommand {

    @CommandDescription(description = "Sets the yaw of the specified entity",
            argnames = {"entity", "yaw"}, name = "SetYaw", parameters = {ParameterType.Entity, ParameterType.Number})
    public SetYawCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 2 && ca.getParams().get(0) instanceof Entity[] && ca.getParams().get(1) instanceof Number) {
            Entity[] ents = (Entity[]) ca.getParams().get(0);
            float yaw = ((Number) ca.getParams().get(1)).floatValue();
            for (Entity e : ents) {
                if (e == null) {
                    continue;
                }
                Location l = e.getLocation();
                l.setYaw(yaw);
                e.teleport(l);
            }
            return true;
        }
        return false;
    }
}