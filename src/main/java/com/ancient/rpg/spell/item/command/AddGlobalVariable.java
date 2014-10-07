package com.ancient.rpg.spell.item.command;

import com.ancient.rpg.exceptions.AncientRPGVariableAlreadyExistsException;
import com.ancient.rpg.parameter.Arguments;
import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.Command;
import com.ancientshores.AncientRPG.Classes.Spells.Variable;

public class AddGlobalVariable extends Command {

	public AddGlobalVariable(int line) {
		super(	line,
				"<html>Creates a global variable which is visible to all spells, can be accessed using normal variables.</html>",
				new Parameter[]{new Parameter(ParameterType.STRING, "variable name", false)});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Arguments args) throws Exception {
		if (!validValues(args.getValues().toArray())) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		String varName = (String) args.getValues().get(0);
		
		if (Variable.globVars.containsKey(varName)) throw new AncientRPGVariableAlreadyExistsException(this.spell.getName() + this.getClass().getName(), this.line, varName);
	
		Variable v = new Variable(varName);
		Variable.globVars.put(varName, v);
	}

}
