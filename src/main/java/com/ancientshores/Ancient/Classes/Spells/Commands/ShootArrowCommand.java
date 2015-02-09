package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class ShootArrowCommand extends ICommand {
    @CommandDescription(description = "<html>The entity shoots an arrow with the specified velocity</html>",
            argnames = {"entity", "velocity"}, name = "ShootArrow", parameters = {ParameterType.Entity, ParameterType.Number})
    public ShootArrowCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Entity[]) {
                final Entity[] target = (Entity[]) ca.getParams().get(0);
                double velo = 2;
                if (ca.getParams().size() > 1 && ca.getParams().get(1) instanceof Number) {
                    velo = ((Number) ca.getParams().get(1)).doubleValue();
                }
                if (target != null && target.length > 0) {
                    for (final Entity targetPlayer : target) {
                        if (targetPlayer == null || !(targetPlayer instanceof LivingEntity)) {
                            continue;
                        }
                        Arrow a = ((LivingEntity) targetPlayer).launchProjectile(Arrow.class);
                        a.setVelocity(a.getVelocity().multiply(velo));
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