package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ThrowPotionCommand extends ICommand {

    @CommandDescription(description = "The entity throws a potion with the specified effects",
            argnames = {"entity", "potionname", "duration", "amplifier"}, name = "ThrowPotion", parameters = {ParameterType.Entity, ParameterType.String, ParameterType.Number, ParameterType.Number})
    public ThrowPotionCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.String, ParameterType.Number, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof String && ca.params.get(2) instanceof Number && ca.params.get(3) instanceof Number) {
                Entity[] ents = (Entity[]) ca.params.get(0);
                int amp = ((Number) ca.params.get(3)).intValue();
                int time = ((Number) ca.params.get(2)).intValue();
                String name = (String) ca.params.get(1);
                PotionEffectType pet = getTypeByName(name);
                for (Entity e : ents) {
                    ThrownPotion tp = (ThrownPotion) e.getWorld().spawnEntity(e.getLocation(), EntityType.THROWN_EXP_BOTTLE);
                    tp.getEffects().add(new PotionEffect(pet, time, amp));
                }
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }

    public PotionEffectType getTypeByName(String name) {
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