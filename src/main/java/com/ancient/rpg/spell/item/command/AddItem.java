package com.ancient.rpg.spell.item.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ancient.rpg.parameter.Arguments;
import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.Command;

public class AddItem extends Command {

	public AddItem(int line) {
		super(line,
				"<html>Adds the specified amount of items to the invenory of the player(s)</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.MATERIAL, "item", false), new Parameter(ParameterType.NUMBER, "amount", false)});
		}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(Arguments args) throws Exception {
		if (!validValues(args.getValues().toArray())) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) args.getValues().get(0);
		Material material = (Material) args.getValues().get(1);
		int amount = (int) args.getValues().get(2);
		
		for (Player p : players) {
			p.getInventory().addItem(new ItemStack(material, amount));
			p.updateInventory();
		}
	}

}
