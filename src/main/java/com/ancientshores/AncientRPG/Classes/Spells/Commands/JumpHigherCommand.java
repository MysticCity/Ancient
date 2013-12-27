package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpHigherCommand extends ICommand {
    @CommandDescription(description = "<html>Lets the target jump the amount of blocks higher for the specified time</html>",
            argnames = {"entity", "duration", "amplifier"}, name = "JumpHigher", parameters = {ParameterType.Entity, ParameterType.Number, ParameterType.Number})
    public JumpHigherCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.Number, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.params.size() == 3 && ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number && ca.params.get(2) instanceof Number) {
            Entity[] players = (Entity[]) ca.params.get(0);
            int time = (int) ((Number) ca.params.get(1)).doubleValue();
            int amplifier = (int) ((Number) ca.params.get(2)).doubleValue();
            for (Entity p : players) {
                if (p == null || !(p instanceof LivingEntity)) {
                    continue;
                }
                int t = Math.round(time / 50);
                if (t == 0) {
                    t = Integer.MAX_VALUE;
                }
                ((LivingEntity) p).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, t, amplifier));
            }
            return true;
        }
        return false;
    }
}
