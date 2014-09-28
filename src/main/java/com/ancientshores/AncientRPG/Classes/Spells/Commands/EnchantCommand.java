package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class EnchantCommand extends ICommand {
    @CommandDescription(description = "<html>Enchants the item in hand with the id (or all if allitems is true) with the enchantment and strength</html>",
            argnames = {"material", "name", "level", "allitems"}, name = "Enchant", parameters = {ParameterType.Number, ParameterType.String, ParameterType.Number, ParameterType.Boolean})
    public EnchantCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Number, ParameterType.String, ParameterType.Number, ParameterType.Boolean};
    }

    @SuppressWarnings("deprecation")
	@Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 4 && ca.getParams().get(0) instanceof Number && ca.getParams().get(1) instanceof String && ca.getParams().get(2) instanceof Number && ca.getParams().get(3) instanceof Boolean) {
            int id = (int) ((Number) ca.getParams().get(0)).doubleValue();
            String name = (String) ca.getParams().get(1);
            int level = (int) ((Number) ca.getParams().get(2)).doubleValue();
            boolean allitems = (Boolean) ca.getParams().get(3);
            Enchantment et = getTypeByName(name);
            if (allitems) {
                for (ItemStack is : ca.getCaster().getInventory().getContents()) {
                    if (is != null && is.getTypeId() == id) {
                        is.addEnchantment(et, level);
                    }
                }
                for (ItemStack is : ca.getCaster().getInventory().getArmorContents()) {
                    if (is != null && is.getTypeId() == id) {
                        is.addEnchantment(et, level);
                    }
                }
            } else if (ca.getCaster().getItemInHand() != null && ca.getCaster().getItemInHand().getTypeId() == id) {
                ca.getCaster().getItemInHand().addEnchantment(et, level);
            }
            if (et == null) {
                return true;
            }
            return true;
        }
        return false;
    }

    public Enchantment getTypeByName(String name) {
        for (Enchantment e : Enchantment.values()) {
            if (e != null && e.getName() != null && e.getName().replace("_", "").equalsIgnoreCase(name)) {
                return e;
            } else if (e != null && e.getName() != null && e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }
}