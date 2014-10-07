package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener;

public class KillCommand extends ICommand {
    @CommandDescription(description = "<html>Kills the target</html>",
            argnames = {"entity"}, name = "Kill", parameters = {ParameterType.Entity})
    public KillCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Entity};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 1) {
            if (ca.getParams().get(0) instanceof Entity[]) {
                final Entity[] entities = (Entity[]) ca.getParams().get(0);
                AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                    public void run() {
                        for (Entity e : entities) {
                            if (e == null || !(e instanceof LivingEntity)) {
                                continue;
                            }
                            LivingEntity ent = (LivingEntity) e;
                            AncientRPGEntityListener.scheduledXpList.put(e.getUniqueId(), ca.getCaster().getUniqueId());
                            EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(ca.getCaster(), ent, DamageCause.CUSTOM, Double.MAX_VALUE);
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            AncientRPGEntityListener.scheduledXpList.remove(e.getUniqueId());
                            if (event.isCancelled() || event.getDamage() == 0) {
                                return;
                            } else {
                                ent.setHealth(0);
                            }
                        }
                    }
                });
            }
        }
        return false;
    }
}