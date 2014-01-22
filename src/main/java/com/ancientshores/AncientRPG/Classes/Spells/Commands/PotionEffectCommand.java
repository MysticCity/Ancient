package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectCommand extends ICommand {
    @CommandDescription(description = "<html>Adds a potioneffect with the specified effect to the entity for the specified duration</html>",
            argnames = {"entity", "name", "duration", "amplifier"}, name = "PotionEffect", parameters = {ParameterType.Entity, ParameterType.String, ParameterType.Number, ParameterType.Number})
    public PotionEffectCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.String, ParameterType.Number, ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 4 && ca.getParams().get(0) instanceof Entity[] && ca.getParams().get(1) instanceof String && ca.getParams().get(2) instanceof Number && ca.getParams().get(3) instanceof Number) {
            Entity[] players = (Entity[]) ca.getParams().get(0);
            String name = (String) ca.getParams().get(1);
            int time = (int) ((Number) ca.getParams().get(2)).doubleValue();
            int amplifier = (int) ((Number) ca.getParams().get(3)).doubleValue();
            PotionEffectType pet = getTypeByName(name);
            if (pet == null) {
                return true;
            }
            for (Entity p : players) {
                if (p == null || !(p instanceof LivingEntity)) {
                    continue;
                }
                int t = Math.round(time / 50);
                if (t == 0) {
                    t = Integer.MAX_VALUE;
                }
                if (((LivingEntity) p).hasPotionEffect(pet)) {
                    ((LivingEntity) p).removePotionEffect(pet);
                }
                ((LivingEntity) p).addPotionEffect(new PotionEffect(pet, t, amplifier));
            }
            return true;
        }
        return false;
    }

    public static PotionEffectType getTypeByName(String name) {
        for (PotionEffectType pt : PotionEffectType.values()) {
            if (pt != null && pt.getName() != null && pt.getName().replace("_", "").equalsIgnoreCase(name)) {
                return pt;
            } else if (pt != null && pt.getName() != null && pt.getName().equalsIgnoreCase(name)) {
                return pt;
            }
        }
        return null;
    }
}