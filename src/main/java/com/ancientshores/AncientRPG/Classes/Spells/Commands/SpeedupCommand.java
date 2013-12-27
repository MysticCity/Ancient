package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedupCommand extends ICommand {
    @CommandDescription(description = "<html>Speeds up the player for the specified amount of time</html>",
            argnames = {"entity", "duration"}, name = "Speedup", parameters = {ParameterType.Entity, ParameterType.Number})
    public SpeedupCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.params.size() >= 2) {
            if (ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number) {
                final Entity[] entities = (Entity[]) ca.params.get(0);
                final int time = (int) ((Number) ca.params.get(1)).doubleValue();
                int amplifier = 2;
                if (ca.params.size() == 3 && ca.params.get(2) instanceof Number) {
                    amplifier = (int) ((Number) ca.params.get(2)).doubleValue();
                }
                for (Entity e : entities) {
                    if (e == null || !(e instanceof LivingEntity)) {
                        continue;
                    }
                    int t = Math.round(time / 50);
                    if (t == 0) {
                        t = Integer.MAX_VALUE;
                    }
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, t, amplifier));

                }
                return true;
            }
        }
        return false;
    }
}
