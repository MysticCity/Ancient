package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;

public class IgniteCommand extends ICommand {

    @CommandDescription(description = "<html>Ignites the target for the specified time</html>",
            argnames = {"entity", "duration"}, name = "Ignite", parameters = {ParameterType.Entity, ParameterType.Number})
    public IgniteCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Entity[] && ca.getParams().get(1) instanceof Number) {
                final Entity[] target = (Entity[]) ca.getParams().get(0);
                final int time = (int) ((Number) ca.getParams().get(1)).doubleValue();
                if (target != null && target.length > 0) {
                    for (final Entity targetPlayer : target) {
                        if (targetPlayer == null) {
                            continue;
                        }
                        targetPlayer.setFireTicks(Math.round(time / 50));
                    }
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}