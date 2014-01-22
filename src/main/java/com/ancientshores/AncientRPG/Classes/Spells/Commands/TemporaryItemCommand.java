package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class TemporaryItemCommand extends ICommand {
    public TemporaryItemCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number, ParameterType.Number, ParameterType.Number};
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().size() >= 3 && ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number && ca.getParams().get(2) instanceof Number && ca.getParams().get(3) instanceof Number) {
                final Player[] players = (Player[]) ca.getParams().get(0);
                final int id = ((Number) ca.getParams().get(1)).intValue();
                final int amount = ((Number) ca.getParams().get(2)).intValue();
                int time = ((Number) ca.getParams().get(3)).intValue();
                for (final Player p : players) {
                    if (p == null) {
                        continue;
                    }
                    final ItemStack is = new ItemStack(id, amount);
                    if (p.getInventory().firstEmpty() > 0) {
                        p.getInventory().addItem(is);
                        p.updateInventory();
                    }
                    int ticks = Math.round(time / 50);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                        @Override
                        public void run() {
                            int anzahl = amount;
                            HashMap<Integer, ? extends ItemStack> items = p.getInventory().all(id);
                            for (ItemStack istack : items.values()) {
                                if (anzahl >= istack.getAmount()) {
                                    p.getInventory().remove(istack);
                                    anzahl -= istack.getAmount();
                                } else {
                                    istack.setAmount(istack.getAmount() - anzahl);
                                    break;
                                }
                            }
                            p.updateInventory();
                        }
                    }, ticks);
                    p.getInventory().addItem(is);
                }
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}