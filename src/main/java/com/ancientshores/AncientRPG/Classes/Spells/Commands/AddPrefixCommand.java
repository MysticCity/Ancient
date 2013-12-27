package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.HelpList;
import org.bukkit.entity.Player;

public class AddPrefixCommand extends ICommand
{
	@CommandDescription(description = "<html>Adds a prefix to the players name if it doesn't already exist</html>",
			argnames = {"player", "prefix"}, name = "AddPrefix", parameters = {ParameterType.Player, ParameterType.String })
	public AddPrefixCommand()
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
				final Player[] players = (Player[]) ca.params.get(0);
				String node = HelpList.replaceChatColor((String) ca.params.get(1));
				if (node != null)
				{
					for (Player p : players)
					{
						if (p == null)
							continue;
						if (!p.getDisplayName().contains(node))
						{
							p.setDisplayName(node + p.getDisplayName());
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
