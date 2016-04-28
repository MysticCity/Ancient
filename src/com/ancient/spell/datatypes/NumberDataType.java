package com.ancient.spell.datatypes;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.DataType;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.spellmaker.Returnable;

public class NumberDataType extends DataType<Number> {
	private Number value;
	private Returnable<Number> valueItem;

	@SuppressWarnings("unchecked")
	public NumberDataType(int line, String value) {
		super(line, "<html>A number data type, which can store <b>numbers</b>.</html>");
		
		try {
			this.value = Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			SpellItem item = SpellParser.parse(value, line);
			if (item instanceof Returnable) this.valueItem = (Returnable<Number>) item;
			else {} // exception. kann nicht verwendet werden.
		}
	}
	
	@Override
	public Number getValue() {
		if (this.valueItem != null) calculateReturn();
		
		return this.value;
	}

	private void calculateReturn() {
		this.value = this.valueItem.getValue();
	}

	@Override
	public Parameter getReturnType() {
		return new Parameter(ParameterType.NUMBER, false);
	}
}
