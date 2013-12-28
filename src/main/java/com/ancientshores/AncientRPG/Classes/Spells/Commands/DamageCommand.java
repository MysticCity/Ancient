package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener;
import com.ancientshores.AncientRPG.Listeners.AncientRPGPlayerListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DamageCommand extends ICommand {

    @CommandDescription(description = "<html>Damages the targeted entity with the specified damage</html>",
            argnames = {"entity", "damage"}, name = "Damage", parameters = {ParameterType.Entity, ParameterType.Number})
    public DamageCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number) {
                final Entity[] target = (Entity[]) ca.params.get(0);
                double damage = ((Number) ca.params.get(1)).floatValue();
                if (target != null && target.length > 0 && target[0] instanceof Entity) {
                    for (final Entity targetPlayer : target) {
                        if (targetPlayer == null || !(targetPlayer instanceof LivingEntity)) {
                            continue;
                        }
                        if (targetPlayer instanceof Player && DamageConverter.isEnabled() && DamageConverter.isWorldEnabled((Player) targetPlayer)) {
                            damage = DamageConverter.reduceDamageByArmor(damage, (Player) targetPlayer);
                        }
                        AncientRPGPlayerListener.damageignored = true;
                        AncientRPGEntityListener.ignoreNextHpEvent = true;
                        ((LivingEntity) targetPlayer).damage(Math.round(damage), ca.caster);
                        AncientRPGPlayerListener.damageignored = false;
                        AncientRPGEntityListener.ignoreNextHpEvent = false;
                        AncientRPGEntityListener.scheduledXpList.put(targetPlayer, ca.caster);
                        AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                            @Override
                            public void run() {
                                AncientRPGEntityListener.scheduledXpList.remove(targetPlayer);
                            }
                        }, Math.round(1000 / 50));
                    }

                    return true;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }
}