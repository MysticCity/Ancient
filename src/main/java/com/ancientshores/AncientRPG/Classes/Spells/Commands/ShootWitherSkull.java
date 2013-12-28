package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.WitherSkull;

public class ShootWitherSkull extends ICommand {
    @CommandDescription(description = "<html>The entity shoots a witherskull</html>",
            argnames = {"shooter"}, name = "ShootWitherSkull", parameters = {ParameterType.Entity})
    public ShootWitherSkull() {
        ParameterType[] buffer = {ParameterType.Entity};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Entity[]) {
                final Entity[] target = (Entity[]) ca.params.get(0);
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