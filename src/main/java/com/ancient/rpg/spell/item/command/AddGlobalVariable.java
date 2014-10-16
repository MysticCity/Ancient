package com.ancient.rpg.spell.item.command;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.CommandParameterizable;
import com.ancientshores.AncientRPG.Classes.Spells.Variable;

public class AddGlobalVariable extends CommandParameterizable {

	public AddGlobalVariable(int line) {
		super(	line,
				"<html>Creates a global variable which is visible to all spells, can be accessed using normal variables.</html>",
				new Parameter[]{new Parameter(ParameterType.STRING, "variable name", false)});
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object[] execute() throws Exception {
		if (!validValues()) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
		
		String varName = (String) parameterValues[0];
		
//		if (Variable.globVars.containsKey(varName)) throw new AncientRPGVariableAlreadyExistsException(this.spell.getName() + this.getClass().getName(), this.line, varName);
	
		Variable v = new Variable(varName);
		Variable.globVars.put(varName, v);
		
		return new Object[]{line};
	}

}
