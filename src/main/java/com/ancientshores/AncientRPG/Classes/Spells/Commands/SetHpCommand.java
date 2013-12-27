package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.HP.DamageConverter;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class SetHpCommand extends ICommand
{
	@CommandDescription(description = "<html>Sets the health of the target to the specified amount</html>",
			argnames = {"entity", "amount"}, name = "SetHp", parameters = {ParameterType.Entity, ParameterType.Number })

	public SetHpCommand()
	{
		ParameterType[] buffer = { ParameterType.Entity, ParameterType.Number };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(EffectArgs ca)
	{
		if (ca.params.size() == 2)
		{
			if (ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number)
			{
				for (Entity e : (Entity[]) ca.params.get(0))
				{
					if(e == null)
						continue;
					if (e instanceof Player && DamageConverter.isEnabled() && DamageConverter.isWorldEnabled((Player) e))
					{
						PlayerData.getPlayerData(((Player) e).getName()).getHpsystem().hp = (int) ((Number) ca.params.get(1)).doubleValue();
						PlayerData.getPlayerData(((Player) e).getName()).getHpsystem().setMinecraftHP();
					} else
					{
						((LivingEntity) e).setHealth((Integer) ca.params.get(1));
					}
				}
				return true;
			}
		}
		return false;
	}

}
