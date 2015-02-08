package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.WitherSkull;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class ShootWitherSkull extends ICommand {
    @CommandDescription(description = "<html>The entity shoots a witherskull</html>",
            argnames = {"shooter"}, name = "ShootWitherSkull", parameters = {ParameterType.Entity})
    public ShootWitherSkull() {
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
                        WitherSkull a = ((LivingEntity) targetPlayer).launchProjectile(WitherSkull.class);
                        a.setShooter((LivingEntity) targetPlayer);
                    }
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}