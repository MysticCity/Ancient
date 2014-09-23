package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class SetPitchCommand extends ICommand {

    @CommandDescription(description = "Sets the pitch of the entity",
            argnames = {"location", "amount"}, name = "SetPitch", parameters = {ParameterType.Entity, ParameterType.Number})
    public SetPitchCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 2 && ca.getParams().get(0) instanceof Entity[] && ca.getParams().get(1) instanceof Number) {
            Entity[] ents = (Entity[]) ca.getParams().get(0);
            float pitch = ((Number) ca.getParams().get(1)).floatValue();
            for (Entity e : ents) {
                if (e == null) {
                    continue;
                }
                Location l = e.getLocation();
                l.setPitch(pitch);
                e.teleport(l);
            }
            return true;
        }
        return false;
    }
}