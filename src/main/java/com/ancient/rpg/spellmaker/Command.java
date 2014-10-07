package com.ancient.rpg.spellmaker;

import com.ancient.rpg.parameter.Arguments;
import com.ancient.rpg.parameter.Parameter;

public abstract class Command extends Parameterizable implements Returnable {
	
	public Command(int line, String description, Parameter[] parameter) {
		super(line, description, parameter);
	}

	public abstract void execute(Arguments args) throws Exception;
}
