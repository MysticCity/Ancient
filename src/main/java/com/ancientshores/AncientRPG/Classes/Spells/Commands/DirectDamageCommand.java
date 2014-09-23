package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener;
import com.ancientshores.AncientRPG.Listeners.AncientRPGPlayerListener;

public class DirectDamageCommand extends ICommand {
    public DirectDamageCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Entity[] && ca.getParams().get(1) instanceof Number) {
                final Entity[] target = (Entity[]) ca.getParams().get(0);
                final float damage = ((Number) ca.getParams().get(1)).floatValue();
                if (target != null && target.length > 0 && target[0] instanceof Entity) {
                    for (final Entity targetPlayer : target) {
                        if (targetPlayer == null || !(targetPlayer instanceof LivingEntity)) {
                            continue;
                        }
                        AncientRPGPlayerListener.damageignored = true;
                        AncientRPGEntityListener.ignoreNextHpEvent = true;
                        ((LivingEntity) targetPlayer).damage(Math.round(damage), ca.getCaster());
                        AncientRPGPlayerListener.damageignored = false;
                        AncientRPGEntityListener.ignoreNextHpEvent = false;
                        AncientRPGEntityListener.scheduledXpList.put(targetPlayer.getUniqueId(), ca.getCaster().getUniqueId());
                        AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                            @Override
                            public void run() {
                                AncientRPGEntityListener.scheduledXpList.remove(targetPlayer.getUniqueId());
                            }
                        }, Math.round(1000 / 50));
                    }

                    return true;
                }
            }
        } catch (Exception ignored) {

        }
        return false;
    }
}