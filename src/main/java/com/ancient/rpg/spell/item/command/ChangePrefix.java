package com.ancient.rpg.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.rpg.parameter.Arguments;
import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.CommandParameterizable;
import com.ancientshores.AncientRPG.AncientRPG;
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
	public void execute(Arguments args) throws Exception {
		if (!validValues(args.getValues().toArray())) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) args.getValues().get(0);
		String prefix = HelpList.replaceChatColor((String) args.getValues().get(1));
		
		for (Player p : players) {
//			if (prefix.equalsIgnoreCase("")) AncientRPG.permissionHandler
		}
	}

}
