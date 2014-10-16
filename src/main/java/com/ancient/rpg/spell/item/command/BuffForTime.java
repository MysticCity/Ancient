package com.ancient.rpg.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.CommandParameterizable;

public class BuffForTime extends CommandParameterizable {

	public BuffForTime(int line) {
		super(line,
				"<html>Buffs the player(s) with the specified buff for the specified time</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.STRING, "buff name", false), new Parameter(ParameterType.NUMBER, "time (in seconds)", false)});
	}

	@Override
	public Object[] execute() throws Exception {
		if (!validValues()) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
	
		Player[] players = (Player[]) parameterValues[0];
		String name = (String) parameterValues[1];
		int time = Integer.parseInt((String) parameterValues[2]);
		
		
		for (Player p : players) {
			
		}
		return new Object[]{line};
		
	}

}
