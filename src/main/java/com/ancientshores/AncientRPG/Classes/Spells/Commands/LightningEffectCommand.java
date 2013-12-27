package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;

public class LightningEffectCommand extends ICommand
{
	@CommandDescription(description = "<html>Lightning effect will strike at the location</html>",
			argnames = {"location"}, name = "LightningEffect", parameters = {ParameterType.Location})
	public LightningEffectCommand()
	{
		ParameterType[] buffer = { ParameterType.Location };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) != null && ca.params.get(0) instanceof Location[] && ((Location[]) ca.params.get(0)).length > 0 && ca.params.get(0) != null)
			{
				final Location[] loc = (Location[]) ca.params.getFirst();
				AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable()
				{

					@Override
					public void run()
					{
						for (Location l : loc)
						{
							if (l == null)
								continue;
							ca.caster.getWorld().strikeLightningEffect(l);
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
