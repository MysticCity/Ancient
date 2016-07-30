package com.ancient.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;

public class BuffForTime extends CommandParameterizable {

	public BuffForTime(int line) {
		super(line,
				"<html>Buffs the player(s) with the specified buff for the specified time</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.STRING, "buff name", false), new Parameter(ParameterType.NUMBER, "time (in seconds)", false)});
	}

	@Override
	public Object[] execute() throws Exception {
		if (!validValues()) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
	
		@SuppressWarnings("unused")
		Player[] players = (Player[]) parameterValues[0];
		@SuppressWarnings("unused")
		String name = (String) parameterValues[1];
		@SuppressWarnings("unused")
		int time = Integer.parseInt((String) parameterValues[2]);
		
		
//		for (Player p : players) {
//			
//		}
		return new Object[]{line};
		
	}

}
