package com.ancientshores.Ancient.Classes.Spells.Commands;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class RemoveItemCommand extends ICommand {
    @CommandDescription(description = "<html>Removes the amount of items from the players inventory, cancels the spell if it fails if cancelonfail is true</html>",
            argnames = {"player", "material", "amount", "cancelonfail"}, name = "RemoveItem", parameters = {ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Boolean})
    public RemoveItemCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Boolean};
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Material && ca.getParams().get(2) instanceof Number && ca.getParams().get(3) instanceof Boolean) {
                final Player[] players = (Player[]) ca.getParams().get(0);
                final Material mat = (Material) ca.getParams().get(1);
                int amountToTake = (int) ((Number) ca.getParams().get(2)).doubleValue();
                final boolean cancelOnFail = (Boolean) ca.getParams().get(3);
                for (Player p : players) {
                    int amountPlayerHas = 0;
                    HashMap<Integer, ? extends ItemStack> items = p.getInventory().all(mat);
                    for (ItemStack istack : items.values()) {
                        amountPlayerHas += istack.getAmount();
                    }
                    if (cancelOnFail && amountPlayerHas < amountToTake) {
                        ca.getSpellInfo().canceled = true;
                        return false;
                    } else {
                        for (Entry<Integer, ? extends ItemStack> entry : items.entrySet()) {
                        	Integer pos = entry.getKey();
                        	ItemStack istack = entry.getValue();
                        	if (amountToTake >= istack.getAmount()) {
                                p.getInventory().clear(pos);
                            	amountToTake -= istack.getAmount();
                            } else {
                                istack.setAmount(istack.getAmount() - amountToTake);
                                break;
                            }
                        }
                    }
                    p.updateInventory();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
}