package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class ThrowEnderpearlCommand extends ICommand {
    @CommandDescription(description = "<html>The shooter throws an enderpearl in the direction he is looking at<br> Parameter 1: the player who throws the egg</html>",
            argnames = {"entity"}, name = "ThrowEnderpearl", parameters = {ParameterType.Entity})
    public ThrowEnderpearlCommand() {
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