package com.ancient.spell;

import com.ancient.spellmaker.Returnable;

public abstract class DataType<T> extends SpellItem implements Returnable<T> {
	public DataType(int line, String description) {
		super(line, description);
	}
}
