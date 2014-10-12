package com.ancient.rpg.spell.datatypes;

import com.ancient.rpg.spell.DataType;

public class BooleanDataType extends DataType<Boolean> {
	private boolean value;
	
	public BooleanDataType(int line, String value) {
		super(line, "<html>A boolean data type, which can store <b>true</b> or <b>false</b>.</html>");
		
		this.value = Boolean.parseBoolean(value);
	}
	
	@Override
	public Boolean getValue() {
		return this.value;
	}
}
