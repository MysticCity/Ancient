package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class ThrowEggCommand extends ICommand {
    @CommandDescription(description = "The entity throws an egg",
            argnames = {"entity"}, name = "ThrowEgg", parameters = {ParameterType.Entity})
    public ThrowEggCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Entity[]) {
                final Entity[] target = (Entity[]) ca.getParams().get(0);
                if (target != null && target.length > 0) {
                    for (final Entity targetPlayer : target) {
                        if (targetPlayer == null || !(targetPlayer instanceof LivingEntity)) {
                            continue;
                        }
                        Egg e = ((LivingEntity) targetPlayer).launchProjectile(Egg.class);
                        e.setShooter((LivingEntity) targetPlayer);
                    }
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}