package com.ancient.rpg.spell.datatypes;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spell.DataType;
import com.ancient.rpg.spell.SpellItem;
import com.ancient.rpg.spell.SpellParser;
import com.ancient.rpg.spellmaker.Returnable;

public class BooleanDataType extends DataType<Boolean> {
	private Boolean value;
	private Returnable<Boolean> valueItem;
	
	@SuppressWarnings("unchecked")
	public BooleanDataType(int line, String value) {
		super(line, "<html>A boolean data type, which can store <b>true</b> or <b>false</b>.</html>");
		
		if (value.toLowerCase().startsWith("true")) {
			this.value = true;
			this.valueItem = null;
		} else if (value.toLowerCase().startsWith("false")) {
			this.value = false;
			this.valueItem = null;
		}
		else {
			SpellItem item = SpellParser.parse(value, line);
		
			if (item instanceof Returnable) this.valueItem = (Returnable<Boolean>) item;
			else {} // exception. kann nicht verwendet werden.
		}
	}
	
	@Override
	public Boolean getValue() {
		if (this.valueItem != null) calculateReturn();
		
		return this.value;
	}

	private void calculateReturn() {
		this.value = this.valueItem.getValue();
	}

	@Override
	public Parameter getReturnType() {
		return new Parameter(ParameterType.BOOLEAN, false);
	}
}
