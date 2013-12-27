package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddItemCommand extends ICommand {
    @CommandDescription(description = "<html>Adds the specified amount of items to the invenory of the player</html>",
            argnames = {"the player", "the itemid", "the amount"}, name = "AddItem", parameters = {ParameterType.Player, ParameterType.Material, ParameterType.Number})
    public AddItemCommand() {
        ParameterType[] buffer = {ParameterType.Player, ParameterType.Material, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.size() == 3 && ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Material && ca.params.get(2) instanceof Number) {
                final Player[] players = (Player[]) ca.params.get(0);
                final Material mat = (Material) ca.params.get(1);
                final int amount = (int) ((Number) ca.params.get(2)).doubleValue();
                for (Player p : players) {
                    if (p == null) {
                        continue;
                    }
                    p.getInventory().addItem(new ItemStack(mat.getId(), amount));
                    p.updateInventory();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
}
