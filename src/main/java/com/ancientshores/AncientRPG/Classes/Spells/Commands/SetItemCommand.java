package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class SetItemCommand extends ICommand {
    @CommandDescription(description = "Sets the item in the specified slot of the player's inventory",
            argnames = {"player", "material", "slot", "amount"}, name = "SetItem", parameters = {ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Number})
    public SetItemCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Number};
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Material && ca.getParams().get(2) instanceof Number && ca.getParams().get(3) instanceof Number) {
                final Player[] players = (Player[]) ca.getParams().get(0);
                final Material mat = (Material) ca.getParams().get(1);
                final int amount = (int) ((Number) ca.getParams().get(3)).doubleValue();
                final int slot = (int) ((Number) ca.getParams().get(2)).doubleValue();
                for (Player p : players) {
                    if (p == null) {
                        continue;
                    }
                    ItemStack is = new ItemStack(mat, amount);
                    p.getInventory().setItem(slot, is);
                    p.updateInventory();
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}