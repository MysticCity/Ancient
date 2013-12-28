package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class RemoveItemCommand extends ICommand {
    @CommandDescription(description = "<html>Removes the amount of items from the players inventory, cancels the spell if it fails if cancelonfail is true</html>",
            argnames = {"player", "material", "amount", "cancelonfail"}, name = "RemoveItem", parameters = {ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Boolean})
    public RemoveItemCommand() {
        ParameterType[] buffer = {ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Boolean};
        this.paramTypes = buffer;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Material && ca.params.get(2) instanceof Number && ca.params.get(3) instanceof Boolean) {
                final Player[] players = (Player[]) ca.params.get(0);
                final Material mat = (Material) ca.params.get(1);
                final int amount = (int) ((Number) ca.params.get(2)).doubleValue();
                final boolean cancelOnFail = (Boolean) ca.params.get(3);
                for (Player p : players) {
                    int zaehler = 0;
                    HashMap<Integer, ? extends ItemStack> items = p.getInventory().all(mat);
                    for (ItemStack istack : items.values()) {
                        zaehler += istack.getAmount();
                    }
                    if (cancelOnFail && zaehler < amount) {
                        ca.so.canceled = true;
                        return false;
                    } else {
                        int anzahl = amount;
                        for (ItemStack istack : items.values()) {
                            if (anzahl >= istack.getAmount()) {
                                p.getInventory().remove(istack);
                                anzahl -= istack.getAmount();
                            } else {
                                istack.setAmount(istack.getAmount() - anzahl);
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