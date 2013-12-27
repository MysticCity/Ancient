package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageModifyCommand extends ICommand {
    @CommandDescription(description = "<html>Modifies the damage of the current damage event</html>",
            argnames = {"modifier"}, name = "DamageModify", parameters = {ParameterType.Number})
    public DamageModifyCommand() {
        ParameterType[] buffer = {ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.params.size() == 1) {
            if (ca.params.get(0) instanceof Number) {
                final int percent = (int) ((Number) ca.params.get(0)).doubleValue();
                if (ca.so.mEvent instanceof EntityDamageEvent) {
                    final EntityDamageEvent event = (EntityDamageEvent) ca.so.mEvent;
                    event.setDamage(Math.round(event.getDamage() * (float) percent / (float) 100));
                    if (event.getDamage() == 0) {
                        event.setCancelled(true);
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
