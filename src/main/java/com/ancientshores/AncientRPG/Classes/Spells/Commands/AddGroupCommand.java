package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Player;

public class AddGroupCommand extends ICommand
{
	@CommandDescription(description = "<html>Sets the player to the permission group with the specified name</html>",
			argnames = {"the player", "the group"}, name = "AddGroup", parameters = {ParameterType.Player, ParameterType.String})
	public AddGroupCommand()
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
				final String node = (String) ca.params.get(1);
				if (node != null)
				{
					for (Player p : players)
					{
						if (p == null)
							continue;
						try
						{
							AncientRPG.permissionHandler.playerAddGroup(p, node);
						} catch (Exception ignored)
						{

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
