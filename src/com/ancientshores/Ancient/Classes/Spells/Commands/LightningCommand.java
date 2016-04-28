package com.ancientshores.Ancient.Classes.Spells.Commands;

import java.util.List;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Listeners.AncientEntityListener;

public class LightningCommand extends ICommand {
	@CommandDescription(description = "<html>Lightning will strike at the location</html>",
			argnames = {"location"}, name = "Lightning", parameters = {ParameterType.Location})
	public LightningCommand() {
		this.paramTypes = new ParameterType[]{ParameterType.Location};
	}

	@Override
	public boolean playCommand(final EffectArgs ca) {
		try {
			if (ca.getParams().get(0) != null && ca.getParams().get(0) instanceof org.bukkit.Location[] && ((org.bukkit.Location[]) ca.getParams().get(0)).length > 0 && ca.getParams().get(0) != null) {
				final org.bukkit.Location[] loc = (org.bukkit.Location[]) ca.getParams().getFirst();
				Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {

					@Override
					public void run() {
						for (org.bukkit.Location l : loc) {
							if (l == null) continue;
							
							final List<org.bukkit.entity.Entity> entityList = l.getWorld().getEntities();
							ca.getCaster().getWorld().strikeLightning(l);
							for (final org.bukkit.entity.Entity e : entityList) {
								if (e.getLocation().distance(l) < 3) {
									if (ca.getCaster().equals(e)) continue;
									AncientEntityListener.scheduledXpList.put(e.getUniqueId(), ca.getCaster().getUniqueId());
									Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {

										@Override
										public void run() {
											AncientEntityListener.scheduledXpList.remove(e.getUniqueId());
										}
									}, 20);
								}
							}
						}
					}
				});
				return true;
			} else if (ca.getSpell().active) {
				ca.getCaster().sendMessage("No target in range");
			}
		} catch (IndexOutOfBoundsException ignored) {
		}
		return false;
	}
}