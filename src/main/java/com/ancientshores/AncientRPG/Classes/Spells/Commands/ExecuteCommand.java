package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

public class ExecuteCommand extends ICommand
{

	@CommandDescription(description = "<html>Executes the spell</html>",
			argnames = {"player", "spellname"}, name = "Execute", parameters = {ParameterType.Player, ParameterType.String})
	public ExecuteCommand()
	{
		ParameterType[] buffer = { ParameterType.Player, ParameterType.String };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof String)
			{
				final Player[] pl = (Player[]) ca.params.get(0);
				final String spellName = (String) ca.params.get(1);
				AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable()
				{
					public void run()
					{
						for (Player p : pl)
						{
							Spell s = AncientRPGClass.getSpell(spellName, PlayerData.getPlayerData(p.getName()));
							CommandPlayer.scheduleSpell(s, p);
						}
					}
				});
				return true;
			}
		} catch (IndexOutOfBoundsException ignored)
		{

		}
		return false;
	}
}
