package com.ancient.rpg.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.CommandParameterizable;
import com.ancientshores.AncientRPG.AncientRPG;

/**
 * @author FroznMine
 *
 * Ersetzt AddGroup im alten System
 */
public class SetPermissionGroup extends CommandParameterizable {

	public SetPermissionGroup(int line) {
		super(	line,
				"<html>Sets the player(s) to the permission group with the specified name</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "Player(s)", true), new Parameter(ParameterType.STRING, "Group name", false)});
	}

	@Override
	public Object[] execute() throws Exception {
		if (!validValues()) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) parameterValues[0];
		String groupName = (String) parameterValues[1];
		
		for (Player p : players) {
			AncientRPG.permissionHandler.playerAddGroup(p, groupName);
		}

		return new Object[]{line};
	}
}
