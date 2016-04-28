package com.ancient.spellmaker;

import com.ancient.parameter.Parameter;

public interface Returnable<T> {
	public Parameter getReturnType();
	public T getValue();
}
