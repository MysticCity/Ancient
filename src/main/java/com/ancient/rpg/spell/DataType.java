package com.ancient.rpg.spell;

import com.ancient.rpg.spellmaker.Returnable;

public abstract class DataType<T> extends SpellItem implements Returnable<T> {
	public DataType(int line, String description) {
		super(line, description);
	}
}
