package com.ancient.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.Ancient;

public class AddPermission extends CommandParameterizable {

	public AddPermission(int line) {
		super(	line,
				"<html>Adds the specified permission to the player(s)</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.STRING, "permission node", false)});
	}

	@Override
	public Object[] execute() throws Exception {
		if (!validValues()) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) parameterValues[0];
		String permissionNode = (String) parameterValues[1];
		
		for (Player p : players) {
			Ancient.permissionHandler.playerAdd(p, permissionNode);
		}
		return new Object[]{line};
	}

}
