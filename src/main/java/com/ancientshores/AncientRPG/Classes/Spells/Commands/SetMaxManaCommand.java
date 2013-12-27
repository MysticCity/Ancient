package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

public class SetMaxManaCommand extends ICommand
{
	@CommandDescription(description = "<html>Sets the maximum amount of mana the player can have</html>",
			argnames = {"player", "amount"}, name = "SetMaxMana", parameters = {ParameterType.Player, ParameterType.Number})

	public SetMaxManaCommand()
	{
		ParameterType[] buffer = { ParameterType.Player, ParameterType.Number };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(EffectArgs ca)
	{
		if (ca.params.size() == 2)
		{
			if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Number)
			{
				for (Player e : (Player[]) ca.params.get(0))
				{
					if (e == null)
						continue;
					PlayerData.getPlayerData(e.getName()).getManasystem().maxmana = (int) ((Number) ca.params.get(1)).doubleValue();
				}
				return true;
			}
		}
		return false;
	}
}
