package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetArmorCommand extends ICommand {
    @CommandDescription(description = "Sets the players armor in the specified slot",
            argnames = {"player", "material", "slot", "amount"}, name = "SetArmor", parameters = {ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Number})
    public SetArmorCommand() {
        ParameterType[] buffer = {ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Material && ca.params.get(2) instanceof Number && ca.params.get(3) instanceof Number) {
                final Entity[] ents = (Entity[]) ca.params.get(0);
                final Material mat = (Material) ca.params.get(1);
                final int slot = (int) ((Number) ca.params.get(2)).doubleValue();
                final int amount = (int) ((Number) ca.params.get(3)).doubleValue();
                for (Entity e : ents) {
                    if (e == null) {
                        continue;
                    }
                    LivingEntity ent = (LivingEntity) e;
                    ItemStack is = new ItemStack(mat, amount);
                    switch (slot) {
                        case 0:
                            ent.getEquipment().setHelmet(is);
                            break;
                        case 1:
                            ent.getEquipment().setChestplate(is);
                            break;
                        case 2:
                            ent.getEquipment().setLeggings(is);
                            break;
                        case 3:
                            ent.getEquipment().setBoots(is);
                            break;
                    }
                    if (e instanceof Player) {
                        ((Player) e).updateInventory();
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}