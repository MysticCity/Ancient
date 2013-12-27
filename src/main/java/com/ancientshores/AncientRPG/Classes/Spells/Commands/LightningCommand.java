package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import java.util.List;

public class LightningCommand extends ICommand
{
	@CommandDescription(description = "<html>Lightning will strike at the location</html>",
			argnames = {"location"}, name = "Lightning", parameters = {ParameterType.Location})
	public LightningCommand()
	{
		ParameterType[] buffer = { ParameterType.Location };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) != null && ca.params.get(0) instanceof org.bukkit.Location[] && ((org.bukkit.Location[]) ca.params.get(0)).length > 0 && ca.params.get(0) != null)
			{
				final org.bukkit.Location[] loc = (org.bukkit.Location[]) ca.params.getFirst();
				com.ancientshores.AncientRPG.AncientRPG.plugin.scheduleThreadSafeTask(com.ancientshores.AncientRPG.AncientRPG.plugin, new Runnable()
				{

					@Override
					public void run()
					{
						for (org.bukkit.Location l : loc)
						{
							if (l == null)
								continue;
							final List<org.bukkit.entity.Entity> entityList = l.getWorld().getEntities();
							ca.caster.getWorld().strikeLightning(l);
							for (final org.bukkit.entity.Entity e : entityList)
							{
								if (e.getLocation().distance(l) < 3)
								{
									if (ca.caster.equals(e))
										continue;
									com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener.scheduledXpList.put(e, ca.caster);
									com.ancientshores.AncientRPG.AncientRPG.plugin.scheduleThreadSafeTask(com.ancientshores.AncientRPG.AncientRPG.plugin, new Runnable()
									{

										@Override
										public void run()
										{
											com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener.scheduledXpList.remove(e);
										}
									}, Math.round(1000 / 50));
								}
							}
						}

					}
				});
				return true;
			} else if (ca.p.active)
			{
				ca.caster.sendMessage("No target in range");
			}
		} catch (IndexOutOfBoundsException ignored)
		{

		}
		return false;
	}
}
