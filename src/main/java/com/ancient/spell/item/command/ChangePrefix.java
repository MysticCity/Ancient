package com.ancient.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.HelpList;

/* TODO	muss noch fertig gemacht werden.
 * 		wenn prefix ueberall sichtbar muss in playerdate ein neuer punkt gemacht werden:
 * 		prefix, suffix u.ae.
 */

/**
 * @author FroznMine
 * 
 * Ersetzt AddPrefix vom alten System
 */
public class ChangePrefix extends CommandParameterizable {

	public ChangePrefix(int line) {
		super(	line,
				"<html>Adds a prefix to the players name.</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.STRING, "prefix (blank to remove prefix)", false)});
	}

	@Override
	public Object[] execute() throws Exception {
		if (!validValues()) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		@SuppressWarnings("unused")
		Player[] players = (Player[]) parameterValues[0];
		@SuppressWarnings("unused")
		String prefix = HelpList.replaceChatColor((String) parameterValues[1]);
		
//		for (Player p : players) {
//			if (prefix.equalsIgnoreCase("")) Ancient.permissionHandler
//		}

		return new Object[]{line};
	}

}
