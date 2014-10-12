package com.ancient.rpg.spell.item.command;

import org.bukkit.entity.Player;

import com.ancient.rpg.parameter.Arguments;
import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.CommandParameterizable;
import com.ancientshores.AncientRPG.AncientRPG;

public class AddPermission extends CommandParameterizable {

	public AddPermission(int line) {
		super(	line,
				"<html>Adds the specified permission to the player(s)</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.STRING, "permission node", false)});
	}

	@Override
	public void execute(Arguments args) throws Exception {
		if (!validValues(args.getValues().toArray())) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) args.getValues().get(0);
		String permissionNode = (String) args.getValues().get(1);
		
		for (Player p : players) {
			AncientRPG.permissionHandler.playerAdd(p, permissionNode);
		}
	}

}
