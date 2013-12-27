package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import org.bukkit.entity.Player;

public class DisruptOnMoveCommand extends ICommand
{
	@CommandDescription(description = "<html> Disrupts the spell if player moves in the specified time</html>",
			argnames = {"player", "duration"}, name = "DisruptOnMove", parameters = {ParameterType.Player, ParameterType.Number})
	public DisruptOnMoveCommand()
	{
		ParameterType[] buffer = { ParameterType.Player, ParameterType.Number };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		if (ca.params.size() == 2)
		{
			if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Number)
			{
				final Player[] players = (Player[]) ca.params.get(0);
				final int time = (int) ((Number) ca.params.get(1)).doubleValue();
				AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable()
				{
					public void run()
					{
						for (final Player p : players)
						{
							AncientRPGSpellListener.addDisruptCommand(p, ca.so, AncientRPGSpellListener.disruptOnMove);
							AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable()
							{
								public void run()
								{
									AncientRPGSpellListener.removeDisruptCommand(p, ca.so, AncientRPGSpellListener.disruptOnMove);
								}
							}, Math.round(time / 50));
						}
					}
				});
			}
		}
		return true;
	}
}
