package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Player;

public class SetInvisibleCommand extends ICommand
{
	@CommandDescription(description = "<html>Makes the player invisible or makes them visible</html>",
			argnames = {"player", "isinvisible"}, name = "SetInvisible", parameters = { ParameterType.Player, ParameterType.Boolean })

	public SetInvisibleCommand()
	{
		ParameterType[] buffer = { ParameterType.Player, ParameterType.Boolean };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Boolean)
			{
				final Player[] target = (Player[]) ca.params.get(0);
				final boolean b = (Boolean) ca.params.get(1);
				for (final Player invisP : target)
				{
					if (invisP == null)
						continue;
					if (b)
					{
						for (Player p : AncientRPG.plugin.getServer().getOnlinePlayers())
						{
							if (p.isOnline())
								p.hidePlayer(invisP);
						}
						InvisibleCommand.invisiblePlayers.add(invisP);
					} else
					{
						for (Player p : AncientRPG.plugin.getServer().getOnlinePlayers())
						{
							p.showPlayer(invisP);
							InvisibleCommand.invisiblePlayers.remove(invisP);
						}
					}
				}
				return true;
			}
		} catch (IndexOutOfBoundsException ignored)
		{

		}
		return false;
	}
}
