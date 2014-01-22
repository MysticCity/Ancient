package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.event.entity.EntityDamageEvent;

public class SetDamageCommand extends ICommand {

    @CommandDescription(description = "<html>Sets the damage of a damage event (attackevent/damageevent/damagebyentityevent)</html>",
            argnames = {"amount"}, name = "SetDamage", parameters = {ParameterType.Number})
    public SetDamageCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Number) {
                final int damage = (int) ((Number) ca.getParams().get(0)).doubleValue();
                if (ca.getSpellInfo().mEvent instanceof EntityDamageEvent) {
                    ((EntityDamageEvent) ca.getSpellInfo().mEvent).setDamage(damage);
                }
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}