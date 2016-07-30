package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Tameable;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class TameCommand extends ICommand {
    @CommandDescription(description = "<html>Tames the target if it is tameable</html>",
            argnames = {"entity"}, name = "Tame", parameters = {ParameterType.Entity})
    public TameCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 1 && ca.getParams().get(0) instanceof Entity[]) {
            Entity[] ents = (Entity[]) ca.getParams().get(0);
            for (Entity e : ents) {
                if (e instanceof Tameable) {
                    ((Tameable) e).setOwner(ca.getCaster());
                }
            }
        }
        return false;
    }
}