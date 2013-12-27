package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SlowCommand extends ICommand {

    @CommandDescription(description = "<html>Slows the target for the specified amount of time</html>",
            argnames = {"entity", "duration"}, name = "Slow", parameters = {ParameterType.Entity, ParameterType.Number})
    public SlowCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number) {
                final Entity[] players = (Entity[]) ca.params.get(0);
                final int time = (int) ((Number) ca.params.get(1)).doubleValue();
                AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for (Entity e : players) {
                            if (e == null || !(e instanceof LivingEntity)) {
                                continue;
                            }
                            int t = Math.round(time / 50);
                            if (t == 0) {
                                t = Integer.MAX_VALUE;
                            }
                            ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, t, 2));
                        }
                    }
                });
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return false;
    }
}
