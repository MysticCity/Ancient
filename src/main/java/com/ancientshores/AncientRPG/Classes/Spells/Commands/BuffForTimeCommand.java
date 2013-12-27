package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

public class BuffForTimeCommand extends ICommand
{

	@CommandDescription(description = "<html>Buffs the player with the specified buff for the specified time in milliseconds</html>",
			argnames = {"player", "buffname", "duration"}, name = "BuffForTime", parameters = {ParameterType.Player, ParameterType.String, ParameterType.Number})
	public BuffForTimeCommand()
	{
		ParameterType[] buffer = { ParameterType.Player, ParameterType.String, ParameterType.Number };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof String && ca.params.get(2) instanceof Number)
			{
				final Player[] pla = (Player[]) ca.params.get(0);
				final String name = (String) ca.params.get(1);
				final int number = (int) ((Number) ca.params.get(2)).doubleValue();
				AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable()
				{
					public void run()
					{
						for (final Player p : pla)
						{
							final Spell s = AncientRPGClass.getSpell(name, PlayerData.getPlayerData(p.getName()));
							if (s != null)
							{
								final int i = s.attachToEventAsBuff(p, ca.caster);
								AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable()
								{
									@Override
									public void run()
									{
										s.detachBuffOfEvent(p, ca.caster, i);
									}

								}, Math.round(number / 50));
							}
						}
					}
				});
				return true;
			}
			return false;
		} catch (IndexOutOfBoundsException e)
		{
			return false;
		}
	}

}
