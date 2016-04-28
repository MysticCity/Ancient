package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.event.entity.ProjectileHitEvent;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class RemoveProjectileCommand extends ICommand {

    @CommandDescription(description = "<html>Removes the projectile which hit something, can only be used in ProjectileHitEvent</html>",
            argnames = {}, name = "RemoveProjectile", parameters = {})
    public RemoveProjectileCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Void};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getSpellInfo().mEvent instanceof ProjectileHitEvent) {
            ((ProjectileHitEvent) ca.getSpellInfo().mEvent).getEntity().remove();
            return true;
        }
        return false;
    }
}