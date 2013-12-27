package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;

public class TeleportCommand extends ICommand
{
	@CommandDescription(description = "<html>Teleports the player to the location</html>",
			argnames = {"location"}, name = "Teleport", parameters = {ParameterType.Location})

	public TeleportCommand()
	{
		ParameterType[] buffer = { ParameterType.Location };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) instanceof Location[])
			{
				final Location[] loc = (Location[]) ca.params.get(0);
				for (Location l : loc)
				{
					if (l == null)
						continue;
					Location lo = ca.caster.getLocation();
					lo.setX(l.getX());
					lo.setY(l.getY());
					lo.setZ(l.getZ());
					ca.caster.teleport(lo);
				}
				return true;
			}
		} catch (Exception ignored)
		{

		}
		return false;
	}

}
