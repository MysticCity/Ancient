package com.ancient.rpg.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.rpg.parameter.Arguments;
import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.Command;
import com.ancientshores.AncientRPG.Mana.ManaSystem;

public class AddMana extends Command {

	public AddMana(int line) {
		super(	line,
				"<html>Adds the specified amount of mana to the players mana system</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.NUMBER, "amount", false)});
	}

	@Override
	public void execute(Arguments args) throws Exception {
		if (!validValues(args.getValues().toArray())) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) args.getValues().get(0);
		int amount = Integer.parseInt((String) args.getValues().get(1));
		
		for (Player p : players) {
			ManaSystem.addManaByUUID(p.getUniqueId(), amount);
		}
	}

}
