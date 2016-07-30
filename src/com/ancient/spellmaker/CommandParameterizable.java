package com.ancient.spellmaker;

import com.ancient.parameter.Parameter;

public abstract class CommandParameterizable extends Parameterizable {
	
	public CommandParameterizable(int line, String description, Parameter[] parameter) {
		super(line, description, parameter);
	}
}
