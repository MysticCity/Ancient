package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

public class SetMaxhpCommand extends ICommand
{
	@CommandDescription(description = "<html>Sets the maximumhealth of the target to the specified amount</html>",
			argnames = {"player", "amount"}, name = "SetMaxHp", parameters = { ParameterType.Player, ParameterType.Number})
	public SetMaxhpCommand()
	{
		ParameterType[] buffer = { ParameterType.Player, ParameterType.Number };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Number)
			{
				final Player[] target = (Player[]) ca.params.get(0);
				final int hp = (int) ((Number) ca.params.get(1)).doubleValue();
				for(Player p : target)
				{
					if(p == null)
						continue;
					PlayerData pd = PlayerData.getPlayerData(p.getName());
					double ratio = pd.getHpsystem().hp / pd.getHpsystem().maxhp;
					pd.getHpsystem().maxhp = hp;
					pd.getHpsystem().hp = Math.round(pd.getHpsystem().maxhp * ratio);
					pd.getHpsystem().setMinecraftHP();
				}
				return true;
			}
		} catch (IndexOutOfBoundsException ignored)
		{

		}
		return false;
	}
}
