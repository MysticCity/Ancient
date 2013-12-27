package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class KillCommand extends ICommand {
    @CommandDescription(description = "<html>Kills the target</html>",
            argnames = {"entity"}, name = "Kill", parameters = {ParameterType.Entity})
    public KillCommand() {
        ParameterType[] buffer = {ParameterType.Entity};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        if (ca.params.size() == 1) {
            if (ca.params.get(0) instanceof Entity[]) {
                final Entity[] entities = (Entity[]) ca.params.get(0);
                AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                    public void run() {
                        for (Entity e : entities) {
                            if (e == null || !(e instanceof LivingEntity)) {
                                continue;
                            }
                            LivingEntity ent = (LivingEntity) e;
                            AncientRPGEntityListener.scheduledXpList.put(e, ca.caster);
                            EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(ca.caster, ent, DamageCause.CUSTOM, Integer.MAX_VALUE);
                            Bukkit.getServer().getPluginManager().callEvent(event);
                            AncientRPGEntityListener.scheduledXpList.remove(e);
                            if (event.isCancelled() || event.getDamage() == 0) {
                                return;
                            } else {
                                ent.setHealth(0);
                            }
                        }
                    }
                });
            }
        } else {

        }
        return false;
    }
}
