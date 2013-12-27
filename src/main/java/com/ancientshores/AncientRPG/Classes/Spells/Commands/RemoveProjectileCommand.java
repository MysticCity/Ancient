package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.event.entity.ProjectileHitEvent;

public class RemoveProjectileCommand extends ICommand {

    @CommandDescription(description = "<html>Removes the projectile which hit something, can only be used in ProjectileHitEvent</html>",
            argnames = {}, name = "RemoveProjectile", parameters = {})
    public RemoveProjectileCommand() {
        ParameterType[] buffer = {ParameterType.Void};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.so.mEvent instanceof ProjectileHitEvent) {
            ((ProjectileHitEvent) ca.so.mEvent).getEntity().remove();
            return true;
        }
        return false;
    }
}
