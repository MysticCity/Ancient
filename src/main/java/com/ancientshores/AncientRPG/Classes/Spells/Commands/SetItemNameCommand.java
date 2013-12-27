package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class SetItemNameCommand extends ICommand
{
	@CommandDescription(description = "Sets the item name of the item in the players inventory",
			argnames = {"player", "slot", "name"}, name = "SetItemName", parameters = {ParameterType.Player, ParameterType.Number, ParameterType.String})
	public SetItemNameCommand()
	{
		ParameterType[] buffer = { ParameterType.Player, ParameterType.Number, ParameterType.String };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(final EffectArgs ca)
	{
		try
		{
			if (ca.params.get(0) instanceof Player[] && ca.params.get(1) instanceof Number && ca.params.get(2) instanceof String)
			{
				final Player[] target = (Player[]) ca.params.get(0);
				final int slot = ((Number)ca.params.get(1)).intValue();
				final String description = (String) ca.params.get(2);
				for (final Player p : target)
				{
					if (p == null)
						continue;
					ItemMeta im = p.getInventory().getItem(slot).getItemMeta();
					im.setDisplayName(description);
					p.getInventory().getItem(slot).setItemMeta(im);
				}
			}
		} catch (IndexOutOfBoundsException e)
		{
			return false;
		}
		return false;
	}
}
