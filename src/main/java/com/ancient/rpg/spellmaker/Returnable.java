package com.ancient.rpg.spellmaker;

import com.ancient.rpg.parameter.Parameter;

public interface Returnable<T> {
	public Parameter getReturnType();
	public T getValue();
}
