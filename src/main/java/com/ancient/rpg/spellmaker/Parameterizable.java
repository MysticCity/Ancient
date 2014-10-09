package com.ancient.rpg.spellmaker;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.spell.SpellItem;
import com.ancient.rpg.stuff.Splitter;

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
	
	/** validParameters is called while parsing a spell. it checks if the in-spell used parameters are valid
	 * 
	 * @param s the parameters written in the spell
	 * @return true if the given parameters are valid, false otherwise
	 */
	public boolean validParameters(String s) {
		String[] arguments = Splitter.splitByArgument(s);
		
		if (arguments.length != this.parameter.length) return false;
		
		for (int i = 0; i < arguments.length; i++) {
			String argument = arguments[i];
			if (argument.startsWith("(")) {
				
//				argument, dass aus argumenten besteht. wieder valid parameters. dafür wieder spellitem erstellen aus name
//				dass wieder durchchecken. desweiteren dann argumentitem.getreturntype == parameter[i]. wenn das alles okay true sonst false
			}
			
			switch (parameter[i].getType()) {
			case BOOLEAN:
				if (argument.equals("true")) break;// korrekter dateityp
				if (argument.equals("false")) break; // ebenfalls korrekt
				
				return false; // ungültig. weder true noch false
			case ENTITY:
				break;
			case LOCATION:
				break;
			case MATERIAL:
				break;
			case NUMBER:
				break;
			case PLAYER:
				break;
			case STRING:
				break;
			default:
				break;
			
			}
		}
		return true;
	}
}
