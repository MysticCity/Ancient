package com.ancient.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.Mana.ManaSystem;

public class AddMana extends CommandParameterizable {

	public AddMana(int line) {
		super(	line,
				"<html>Adds the specified amount of mana to the players mana system</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.NUMBER, "amount", false)});
	}

	@Override
	public Object[] execute() throws Exception {
		if (!this.validValues()) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) parameterValues[0];
		int amount = Integer.parseInt((String) parameterValues[1]);
		
		for (Player p : players) {
			ManaSystem.addManaByUUID(p.getUniqueId(), amount);
		}
		return new Object[]{line};
	}

}
