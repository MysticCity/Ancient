package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class ShootArrowCommand extends ICommand {
    @CommandDescription(description = "<html>The entity shoots an arrow with the specified velocity</html>",
            argnames = {"entity", "velocity"}, name = "ShootArrow", parameters = {ParameterType.Entity, ParameterType.Number})
    public ShootArrowCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Entity[]) {
                final Entity[] target = (Entity[]) ca.params.get(0);
                double velo = 2;
                if (ca.params.size() > 1 && ca.params.get(1) instanceof Number) {
                    velo = ((Number) ca.params.get(1)).doubleValue();
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
