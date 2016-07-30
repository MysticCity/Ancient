package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.HP.CreatureHp;
import com.ancientshores.Ancient.Listeners.AncientEntityListener;
import com.ancientshores.Ancient.Listeners.AncientPlayerListener;

public class DamageCommand extends ICommand {

	@CommandDescription(description = "<html>Damages the targeted entity with the specified damage</html>",
			argnames = {"entity", "damage"}, name = "Damage", parameters = {ParameterType.Entity, ParameterType.Number})
	public DamageCommand() {
		this.paramTypes = new ParameterType[]{ParameterType.Entity, ParameterType.Number};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		try {
			if (ca.getParams().get(0) instanceof Entity[] && ca.getParams().get(1) instanceof Number) {
				final Entity[] targets = (Entity[]) ca.getParams().get(0);
				double damage = ((Number) ca.getParams().get(1)).floatValue();
				if (targets != null && targets.length > 0 && targets[0] instanceof Entity) {
					
					for (final Entity target : targets) {
						if (target == null || !(target instanceof LivingEntity))
							continue;
						
						CreatureHp.getCreatureHpByEntity((LivingEntity) target);
						
						AncientPlayerListener.damageignored = true;
                        AncientEntityListener.ignoreNextHpEvent = true;
                        ((LivingEntity) target).damage(damage, ca.getCaster());
                        AncientPlayerListener.damageignored = false;
                        AncientEntityListener.ignoreNextHpEvent = false;
                        
						AncientEntityListener.scheduledXpList.put(target.getUniqueId(), ca.getCaster().getUniqueId());
						Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {
							@Override
							public void run() {
								AncientEntityListener.scheduledXpList.remove(target.getUniqueId());
							}
						}, Math.round(1000 / 50));
					}
					return true;
				}
			}
		} catch (IndexOutOfBoundsException ignored) {}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}