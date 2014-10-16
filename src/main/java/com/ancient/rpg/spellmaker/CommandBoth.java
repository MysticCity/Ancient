package com.ancient.rpg.spellmaker;

import com.ancient.rpg.parameter.Parameter;

public abstract class CommandBoth<T> extends Parameterizable implements Returnable<T> {
	
	public CommandBoth(int line, String description, Parameter[] parameter) {
		super(line, description, parameter);
	}

	@Override
	public abstract Parameter getReturnType();
}
