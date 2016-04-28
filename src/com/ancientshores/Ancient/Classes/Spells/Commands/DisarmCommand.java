package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;

public class DisarmCommand extends ICommand {
    @CommandDescription(description = "<html>Disarms the player for the specified time</html>",
            argnames = {"player", "duration"}, name = "Disarm", parameters = {ParameterType.Player, ParameterType.Number})
    public DisarmCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number) {
                final Player[] target = (Player[]) ca.getParams().get(0);
                final int time = (int) ((Number) ca.getParams().get(1)).doubleValue();
                if (target != null && target.length > 0) {
                    Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {
                        @Override
                        public void run() {
                            for (final Player p : target) {
                                if (p == null) {
                                    continue;
                                }
                                if (time > 0 && !(AncientSpellListener.disarmList.containsKey(p.getUniqueId()))) {
                                    final ItemStack is = p.getItemInHand();
                                    AncientSpellListener.disarmList.put(p.getUniqueId(), is);
                                    p.setItemInHand(null);
                                    Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {
                                        @Override
                                        public void run() {
                                            if (p.getItemInHand() == null) {
                                                p.setItemInHand(is);
                                            } else {
                                                p.getInventory().addItem(is);
                                            }
                                            AncientSpellListener.disarmList.remove(p.getUniqueId());
                                        }
                                    }, Math.round(time / 50));
                                }
                            }
                        }
                    });
                    return true;
                } else if (ca.getSpell().active) {
                    ca.getCaster().sendMessage("Target not found");
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}