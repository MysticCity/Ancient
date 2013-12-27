package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

import java.util.List;

public class GrenadeCommand extends ICommand
{
	@CommandDescription(description = "<html>The caster throws tnt which explodes after the time</html>",
			argnames = {"time"}, name = "Grenade", parameters = {ParameterType.Number})
	public GrenadeCommand()
	{
		ParameterType[] buffer = { ParameterType.Number };
		this.paramTypes = buffer;
	}

	public boolean playCommand(final EffectArgs ca)
	{
		if (ca.params.size() == 1)
		{
			if (ca.params.get(0) instanceof Number)
			{
				final int time = (int) ((Number) ca.params.get(0)).doubleValue();
				Location spawnloc = ca.caster.getLocation().add(0, 2, 0);
				final TNTPrimed grenade = ca.caster.getWorld().spawn(spawnloc, TNTPrimed.class);
				Vector throwv = ca.caster.getLocation().getDirection().normalize();
				grenade.setVelocity(throwv);
				int ticks = Math.round(time / 50);
				if (ticks == 0)
					ticks = 1;
				grenade.setFuseTicks(ticks);
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable()
				{
					public void run()
					{
						final List<Entity> entityList = grenade.getLocation().getWorld().getEntities();
						for (final Entity e : entityList)
						{
							if (e.getLocation().distance(grenade.getLocation()) < 4)
							{
								if (ca.caster.equals(e))
									continue;
								AncientRPGEntityListener.scheduledXpList.put(e, ca.caster);
								AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable()
								{

									@Override
									public void run()
									{
										AncientRPGEntityListener.scheduledXpList.remove(e);
									}
								}, Math.round(2));
							}
						}
					}
				}, ticks);

			}
			return true;
		}
		return false;
	}
}
