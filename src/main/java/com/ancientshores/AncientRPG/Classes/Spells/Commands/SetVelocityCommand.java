package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class SetVelocityCommand extends ICommand
{
	@CommandDescription(description = "<html>Sets the velo of the entity</html>",
			argnames = {"entity", "forward", "sideward", "upward"}, name = "SetVelocity", parameters = {ParameterType.Entity, ParameterType.Number, ParameterType.Number, ParameterType.Number})
	public SetVelocityCommand()
	{
		ParameterType[] buffer = { ParameterType.Entity, ParameterType.Number, ParameterType.Number, ParameterType.Number };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		if (ca.params.size() == 4)
		{
			if (ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number && ca.params.get(2) instanceof Number && ca.params.get(3) instanceof Number)
			{
				final Entity[] target = (Entity[]) ca.params.get(0);
				double forward = ((Number) ca.params.get(1)).doubleValue();
				double sideward = ((Number) ca.params.get(2)).doubleValue();
				double upward = ((Number) ca.params.get(3)).doubleValue();
				if (target != null && target.length > 0)
				{
					for (final Entity targetPlayer : target)
					{
						if (targetPlayer == null || !(targetPlayer instanceof LivingEntity))
							continue;
						Vector v = targetPlayer.getLocation().getDirection();
						double x;
						double y = upward;
						double z;
						if (Math.abs(v.getX()) > Math.abs(v.getZ()))
						{
							x = forward * v.getX();
							z = sideward * v.getZ();
						} else
						{
							x = sideward * v.getX();
							z = forward * v.getZ();
						}
						targetPlayer.setVelocity(new Vector(x, y, z));
					}
					return true;
				}
			}
		}
		return false;
	}
}
