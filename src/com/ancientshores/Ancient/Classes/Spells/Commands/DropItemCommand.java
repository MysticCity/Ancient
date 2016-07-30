package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class DropItemCommand extends ICommand {
    @CommandDescription(description = "<html>Drops the specified amount of items at the location</html>",
            argnames = {"location", "material", "amount"}, name = "DropItem", parameters = {ParameterType.Location, ParameterType.Material, ParameterType.Number})
    public DropItemCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Material, ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 3 && ca.getParams().get(0) instanceof Location[] && ca.getParams().get(1) instanceof Material && ca.getParams().get(2) instanceof Number) {
            Location locs[] = (Location[]) ca.getParams().get(0);
            Material mat = (Material) ca.getParams().get(1);
            int amount = (int) ((Number) ca.getParams().get(2)).doubleValue();
            for (Location l : locs) {
                while (amount > 0) {
                    int remove;
                    if (amount > 64) {
                        remove = 64;
                        amount -= 64;
                    } else {
                        remove = amount;
                        amount = 0;
                    }
                    ItemStack is = new ItemStack(mat, remove);
                    l.getWorld().dropItem(l, is);
                }
            }
            return true;
        }
        return false;
    }
}