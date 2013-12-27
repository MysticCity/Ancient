package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonCommand extends ICommand {
    @CommandDescription(description = "<html>Poisons the target for the specified amount of time</html>",
            argnames = {"entity", "duration", "amplifier"}, name = "Poison", parameters = {ParameterType.Entity, ParameterType.Number, ParameterType.Number})
    public PoisonCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.Number, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.size() == 3 && ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number && ca.params.get(2) instanceof Number) {
                final Entity[] entities = (Entity[]) ca.params.getFirst();
                int time = (int) ((Number) ca.params.get(1)).doubleValue();
                int amplifier = (int) ((Number) ca.params.get(2)).doubleValue();
                for (Entity e : entities) {
                    if (!(e instanceof LivingEntity)) {
                        break;
                    }
                    int t = Math.round(time / 50);
                    if (t == 0) {
                        t = Integer.MAX_VALUE;
                    }
                    ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.POISON, t, amplifier));
                }
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return false;
    }
}
