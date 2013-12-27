package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class StunCommand extends ICommand {
    @CommandDescription(description = "<html>Stuns the player for the specified amount of time</html>",
            argnames = {"entity", "duration"}, name = "Stun", parameters = {ParameterType.Entity, ParameterType.Number})
    public StunCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number) {
                final Entity[] target = (Entity[]) ca.params.get(0);
                final int time = (int) ((Number) ca.params.get(1)).doubleValue();
                if (target != null && target.length > 0 && target[0] instanceof Entity) {
                    for (final Entity e : target) {
                        if (e == null || !(e instanceof LivingEntity)) {
                            continue;
                        }
                        AncientRPGEntityListener.stun(e);
                        final Location l = e.getLocation();
                        final int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(AncientRPG.plugin, new Runnable() {

                            @Override
                            public void run() {
                                if (!AncientRPGEntityListener.StunList.contains(e)) {
                                    return;
                                }
                                e.teleport(l);
                            }

                        }, 1, 1);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                            public void run() {
                                Bukkit.getScheduler().cancelTask(id);
                                AncientRPGEntityListener.unstun(e);
                            }
                        }, Math.round(time / 50));
                    }
                    return true;
                } else if (ca.p.active) {
                    ca.caster.sendMessage("Target not found");
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}