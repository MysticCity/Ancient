package com.ancient.rpg.spell;

public abstract class DataType<T> extends SpellItem {
	public DataType(int line, String description) {
		super(line, description);
	}
	public abstract T getValue();
}
