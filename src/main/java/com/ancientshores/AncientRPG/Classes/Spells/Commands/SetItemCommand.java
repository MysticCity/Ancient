package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetItemCommand extends ICommand
{
	@CommandDescription(description = "Sets the item in the specified slot of the player's inventory",
			argnames = {"player", "material", "slot", "amount"}, name = "SetItem", parameters = {ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Number})
	public SetItemCommand()
	{
		ParameterType[] buffer = { ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Number  };
		this.paramTypes = buffer;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Material && ca.params.get(2) instanceof Number && ca.params.get(3) instanceof Number)
			{
				final Player[] players = (Player[]) ca.params.get(0);
				final Material mat = (Material) ca.params.get(1);
				final int amount = (int) ((Number) ca.params.get(3)).doubleValue();
				final int slot = (int) ((Number) ca.params.get(2)).doubleValue();
				for (Player p : players)
				{
					if (p == null)
						continue;
					ItemStack is = new ItemStack(mat, amount);
					p.getInventory().setItem(slot, is);
					p.updateInventory();
				}
			}
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}
}
