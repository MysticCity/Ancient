package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class ChargeEntityToCommand extends ICommand
{
	@CommandDescription(description = "<html>Charges the entity to the location with the speed in blocks/s stops after maxdistance</html>",
			argnames = {"entity", "location", "speed", "maxdistance"}, name = "ChargeEntityTo", parameters = {ParameterType.Entity, ParameterType.Location, ParameterType.Number, ParameterType.Number})

	public ChargeEntityToCommand()
	{
		ParameterType[] buffer = { ParameterType.Entity, ParameterType.Location, ParameterType.Number, ParameterType.Number };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.size() >= 4 && ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Location[] && ca.params.get(2) instanceof Number && ca.params.get(3) instanceof Number)
			{
				final Entity[] ent = (Entity[]) ca.params.get(0);
				final Location[] loc = (Location[]) ca.params.get(1);
				double bps = ((Number) ca.params.get(2)).doubleValue();
				double maxdistance = ((Number) ca.params.get(3)).doubleValue();
				if (loc != null && loc.length > 0 && loc[0] != null)
				{
					for (final Entity e : ent)
					{
						boolean broken = false;
						for (Location l : loc)
						{
							if (l == null)
								continue;
							double distance = e.getLocation().distance(l);
							int deltatime = 1;
							double bpt = bps / 20;
							double gestime = distance / bpt;
							final double xps = (l.getX() - e.getLocation().getX()) / gestime;
							final double yps = (l.getY() - e.getLocation().getY()) / gestime;
							final double zps = (l.getZ() - e.getLocation().getZ()) / gestime;
							Location curLocation = e.getLocation();
							int time = 0;
							for (double i = 0; i < gestime; i += 1)
							{
								curLocation.add(new Location(e.getWorld(), xps, yps, zps));
								final Location portLocation = new Location(e.getWorld(), curLocation.getX(), curLocation.getY(), curLocation.getZ());
								AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable()
								{
									@Override
									public void run()
									{
										Location lo = e.getLocation();
										lo.setX(portLocation.getX());
										lo.setY(portLocation.getY());
										lo.setZ(portLocation.getZ());
										e.teleport(lo);
									}
								}, time);
								time += deltatime;
								if (maxdistance > 0 && Math.abs(xps * i) > maxdistance)
								{
									broken = true;
									break;
								}
							}
							if (!broken)
							{
								final Location portLocation = new Location(e.getWorld(), l.getX(), l.getY(), l.getZ());
								AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable()
								{
									@Override
									public void run()
									{
										Location lo = e.getLocation();
										lo.setX(portLocation.getX());
										lo.setY(portLocation.getY());
										lo.setZ(portLocation.getZ());
										e.teleport(lo);
									}
								}, time);
							}
						}
					}
					return true;
				}
			}
		} catch (IndexOutOfBoundsException e)
		{
			return false;
		}
		return false;
	}
}
