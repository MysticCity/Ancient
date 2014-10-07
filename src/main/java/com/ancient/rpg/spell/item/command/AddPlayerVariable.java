package com.ancient.rpg.spell.item.command;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.ancient.rpg.exceptions.AncientRPGVariableAlreadyExistsException;
import com.ancient.rpg.parameter.Arguments;
import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.Command;
import com.ancientshores.AncientRPG.Classes.Spells.Variable;

public class AddPlayerVariable extends Command {

	public AddPlayerVariable(int line) {
		super(	line,
				"<html>Creates a player variable which is visible to all of the players spells, can be accessed using normal variables.</html>",
				new Parameter[]{new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.STRING, "variable name", false)});
	}

	@Override
	public void execute(Arguments args) throws Exception {
		if (!validValues(args.getValues().toArray())) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		Player[] players = (Player[]) args.getValues().get(0);
		String varName = (String) args.getValues().get(1);
		
		for (Player p : players) {
			if (!Variable.playerVars.containsKey(p.getUniqueId())) Variable.playerVars.put(p.getUniqueId(), new HashMap<String, Variable>());
			
			if (Variable.playerVars.get(p.getUniqueId()).containsKey(varName)) throw new AncientRPGVariableAlreadyExistsException(this.spell.getName(), this.getClass().getName(), this.line, varName);
			
			Variable v = new Variable(varName);
			Variable.playerVars.get(p.getUniqueId()).put(varName, v);	
		}
	}

}
