package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.event.entity.EntityDamageEvent;

public class SetDamageCommand extends ICommand {

    @CommandDescription(description = "<html>Sets the damage of a damage event (attackevent/damageevent/damagebyentityevent)</html>",
            argnames = {"amount"}, name = "SetDamage", parameters = {ParameterType.Number})
    public SetDamageCommand() {
        ParameterType[] buffer = {ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Number) {
                final int damage = (int) ((Number) ca.params.get(0)).doubleValue();
                if (ca.so.mEvent instanceof EntityDamageEvent) {
                    ((EntityDamageEvent) ca.so.mEvent).setDamage(damage);
                }
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return false;
    }
}