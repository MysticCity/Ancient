package com.ancient.rpg.spell.datatypes;

import com.ancient.rpg.spell.DataType;
import com.ancient.rpg.spell.SpellParser;

public class StringDataType extends DataType<String> {
	private String value;
	
	public StringDataType(int line, String value) {
		super(line, "<html>A string data type, which can store <b>text</b>.</html>");
		
		if (value.startsWith("\"")) this.value = value;
		else {
			SpellParser.parse(value, line);
		}
	}

	@Override
	public String getValue() {
		return this.value;
	}
}
