package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class AddItemCommand extends ICommand {
	@CommandDescription(description = "<html>Adds the specified amount of items to the invenory of the player</html>",
			argnames = {"the player", "the itemid", "the amount"}, name = "AddItem", parameters = {ParameterType.Player, ParameterType.Number, ParameterType.Number})
	public AddItemCommand() {
		this.paramTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number, ParameterType.Number};
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean playCommand(final EffectArgs ca) {
		try {
			if (ca.getParams().size() == 3 && ca.getParams().get(0) instanceof Player[] && ca.getParams().get(1) instanceof Number && ca.getParams().get(2) instanceof Number) {
				final Player[] players = (Player[]) ca.getParams().get(0);
				final Material mat = Material.getMaterial(((Number) ca.getParams().get(1)).intValue());
				final int amount = ((Number) ca.getParams().get(2)).intValue();
				for (Player p : players) {
					if (p == null) {
						continue;
					}
					p.getInventory().addItem(new ItemStack(mat, amount));
					p.updateInventory();
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
}