package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DropItemCommand extends ICommand {
    @CommandDescription(description = "<html>Drops the specified amount of items at the location</html>",
            argnames = {"location", "material", "amount"}, name = "DropItem", parameters = {ParameterType.Location, ParameterType.Material, ParameterType.Number})
    public DropItemCommand() {
        ParameterType[] buffer = {ParameterType.Location, ParameterType.Material, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.params.size() == 3 && ca.params.get(0) instanceof Location[] && ca.params.get(1) instanceof Material && ca.params.get(2) instanceof Number) {
            Location locs[] = (Location[]) ca.params.get(0);
            Material mat = (Material) ca.params.get(1);
            int amount = (int) ((Number) ca.params.get(2)).doubleValue();
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
