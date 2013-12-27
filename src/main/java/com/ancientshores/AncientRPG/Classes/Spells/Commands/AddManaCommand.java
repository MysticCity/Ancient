package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Mana.ManaSystem;
import org.bukkit.entity.Player;

public class AddManaCommand extends ICommand
{
	@CommandDescription(description = "<html>Adds the specified amount of mana to the players mana system</html>",
			argnames = {"the player", "amount"}, name = "AddMana", parameters = {ParameterType.Player, ParameterType.Number})

	public AddManaCommand()
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
					ManaSystem.addManaByName(e.getName(), (int) ((Number) ca.params.get(1)).doubleValue());
				}
				return true;
			}
		}
		return false;
	}
}
