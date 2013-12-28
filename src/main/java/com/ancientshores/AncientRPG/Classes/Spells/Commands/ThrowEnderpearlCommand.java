package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class ThrowEnderpearlCommand extends ICommand {
    @CommandDescription(description = "<html>The shooter throws an enderpearl in the direction he is looking at<br> Parameter 1: the player who throws the egg</html>",
            argnames = {"entity"}, name = "ThrowEnderpearl", parameters = {ParameterType.Entity})
    public ThrowEnderpearlCommand() {
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
                        EnderPearl e = ((LivingEntity) targetPlayer).launchProjectile(EnderPearl.class);
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