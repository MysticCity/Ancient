package com.ancient.rpg.spellmaker;

import com.ancient.rpg.parameter.Parameter;

public abstract class CommandParameterizable extends Parameterizable {
	
	public CommandParameterizable(int line, String description, Parameter[] parameter) {
		super(line, description, parameter);
	}
}
