package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

public class RemovePotionEffectCommand extends ICommand {
    @CommandDescription(description = "<html>Removes the specified potion effect from the player</html>",
            argnames = {"entity", "effectname"}, name = "RemovePotion", parameters = {ParameterType.Entity, ParameterType.String})
    public RemovePotionEffectCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.String};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.params.size() == 2 && ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof String) {
            Entity[] players = (Entity[]) ca.params.get(0);
            String name = (String) ca.params.get(1);
            PotionEffectType pet = getTypeByName(name);
            if (pet == null) {
                return true;
            }
            for (Entity p : players) {
                if (p == null || !(p instanceof LivingEntity)) {
                    continue;
                }
                if (((LivingEntity) p).hasPotionEffect(pet)) {
                    ((LivingEntity) p).removePotionEffect(pet);
                }
            }
            return true;
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