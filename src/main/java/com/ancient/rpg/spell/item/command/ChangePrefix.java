package com.ancient.rpg.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.CommandParameterizable;
import com.ancientshores.AncientRPG.HelpList;

/* TODO	muss noch fertig gemacht werden.
 * 		wenn prefix ??berall sichtbar muss in playerdate ein neuer punkt gemacht werden:
 * 		prefix, suffix u.??.
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
		
		Player[] players = (Player[]) parameterValues[0];
		String prefix = HelpList.replaceChatColor((String) parameterValues[1]);
		
		for (Player p : players) {
//			if (prefix.equalsIgnoreCase("")) AncientRPG.permissionHandler
		}

		return new Object[]{line};
	}

}
