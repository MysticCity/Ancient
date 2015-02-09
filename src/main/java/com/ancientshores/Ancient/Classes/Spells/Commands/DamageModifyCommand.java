package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.event.entity.EntityDamageEvent;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class DamageModifyCommand extends ICommand {
    @CommandDescription(description = "<html>Modifies the damage of the current damage event</html>",
            argnames = {"modifier"}, name = "DamageModify", parameters = {ParameterType.Number})
    public DamageModifyCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 1) {
            if (ca.getParams().get(0) instanceof Number) {
                final int percent = (int) ((Number) ca.getParams().get(0)).doubleValue();
                if (ca.getSpellInfo().mEvent instanceof EntityDamageEvent) {
                    final EntityDamageEvent event = (EntityDamageEvent) ca.getSpellInfo().mEvent;
                    event.setDamage(Math.round(event.getDamage() * (float) percent / (float) 100));
                    if (event.getDamage() == 0)
                        event.setCancelled(true);
                    return true;
                }
            }
        }
        return false;
    }
}