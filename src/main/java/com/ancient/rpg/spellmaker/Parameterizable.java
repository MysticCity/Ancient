package com.ancient.rpg.spellmaker;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.spell.SpellItem;

public abstract class Parameterizable extends SpellItem {
	protected Parameter[] parameter;
	
	public Parameterizable(int line, String description, Parameter[] parameter) {
		super(line, description);
		
		this.parameter = parameter;
	}
	
	public boolean validValues(Object[] values) {
		if (values.length != parameter.length) return false;
		
		for (int i = 0; i < this.parameter.length; i++) {
			if (values[i] == null) return false;
			if (!values[i].getClass().equals(parameter[i].getType().getClassType())) return false;
			if (parameter[i].isArray()) {
				if (!(values[i] instanceof Object[])) return false;
				for (Object o : values) {
					if (o == null) return false;
				}
			}
			
		}
		return true;
	}
}
