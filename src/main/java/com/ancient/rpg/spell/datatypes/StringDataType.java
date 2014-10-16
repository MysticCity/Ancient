package com.ancient.rpg.spell.datatypes;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spell.DataType;
import com.ancient.rpg.spell.SpellItem;
import com.ancient.rpg.spell.SpellParser;
import com.ancient.rpg.spellmaker.Returnable;

public class StringDataType extends DataType<String> {
	private String value;
	private Returnable<String> valueItem;
	
	@SuppressWarnings("unchecked")
	public StringDataType(int line, String value) {
		super(line, "<html>A string data type, which can store <b>text</b>.</html>");
		
		if (value.startsWith("\"")) {
			this.value = value.substring(1, value.length());
			this.valueItem = null;
		}
		else {
			SpellItem item = SpellParser.parse(value, line);
		
			if (item instanceof Returnable) this.valueItem = (Returnable<String>) item;
			else {} // exception. kann nicht verwendet werden.
		}
	}

	@Override
	public String getValue() {
		if (this.valueItem != null) calculateReturn();
		
		return this.value;
	}

	private void calculateReturn() {
		this.value = this.valueItem.getValue();
	}

	@Override
	public Parameter getReturnType() {
		return new Parameter(ParameterType.STRING, false);
	}
}
