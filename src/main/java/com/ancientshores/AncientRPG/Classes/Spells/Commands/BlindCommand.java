package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlindCommand extends ICommand {
    @CommandDescription(description = "<html>Blinds the specified player for the entered amount of time in milliseconds.</html>",
            argnames = {"entity", "duration", "amplifier"}, name = "Blind", parameters = {ParameterType.Entity, ParameterType.Number, ParameterType.Number})
    public BlindCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number, ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 3 && ca.getParams().get(0) instanceof Entity[] && ca.getParams().get(1) instanceof Number && ca.getParams().get(2) instanceof Number) {
            Entity[] players = (Entity[]) ca.getParams().get(0);
            int time = (int) ((Number) ca.getParams().get(1)).doubleValue();
            int amplifier = (int) ((Number) ca.getParams().get(2)).doubleValue();
            for (Entity p : players) {
                if (p == null || !(p instanceof LivingEntity)) {
                    continue;
                }
                int t = Math.round(time / 50);
                if (t == 0) {
                    t = Integer.MAX_VALUE;
                }
                ((LivingEntity) p).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, t, amplifier));
            }
            return true;
        }
        return false;
    }
}