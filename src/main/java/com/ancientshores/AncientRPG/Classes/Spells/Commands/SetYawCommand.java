package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class SetYawCommand extends ICommand
{

	@CommandDescription(description = "Sets the yaw of the specified entity",
			argnames = {"entity", "yaw"}, name = "SetYaw", parameters = {ParameterType.Entity, ParameterType.Number})
	public SetYawCommand()
	{
		ParameterType[] buffer = { ParameterType.Entity, ParameterType.Number };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		if (ca.params.size() == 2 && ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number)
		{
			Entity[] ents = (Entity[]) ca.params.get(0);
			float yaw = ((Number) ca.params.get(1)).floatValue();
			for (Entity e : ents)
			{
				if (e == null)
					continue;
				Location l = e.getLocation();
				l.setYaw(yaw);
				e.teleport(l);

			}
			return true;
		}
		return false;
	}
}
