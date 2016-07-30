package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class ThrowSnowballCommand extends ICommand {
    @CommandDescription(description = "<html>The shooter throws a snowball in the direction he is looking at<br> Parameter 1: the player who throws the snowball</html>",
            argnames = {"entity"}, name = "ThrowSnowball", parameters = {ParameterType.Entity})
    public ThrowSnowballCommand() {
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
                        Snowball s = ((LivingEntity) targetPlayer).launchProjectile(Snowball.class);
                        s.setShooter((LivingEntity) targetPlayer);
                    }
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}