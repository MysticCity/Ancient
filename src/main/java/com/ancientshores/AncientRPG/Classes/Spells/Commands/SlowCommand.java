package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class SlowCommand extends ICommand {

    @CommandDescription(description = "<html>Slows the target for the specified amount of time</html>",
            argnames = {"entity", "duration"}, name = "Slow", parameters = {ParameterType.Entity, ParameterType.Number})
    public SlowCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Entity[] && ca.getParams().get(1) instanceof Number) {
                final Entity[] players = (Entity[]) ca.getParams().get(0);
                final int time = (int) ((Number) ca.getParams().get(1)).doubleValue();
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