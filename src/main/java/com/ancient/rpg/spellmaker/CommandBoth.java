package com.ancient.rpg.spellmaker;

import com.ancient.rpg.parameter.Parameter;

public abstract class CommandBoth<T> extends Parameterizable implements Returnable {

	public CommandBoth() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract Parameter getReturnType();

	public abstract T execute();
}
