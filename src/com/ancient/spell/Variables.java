package com.ancient.spell;

import java.util.HashMap;

public class Variables {
	private static HashMap<String, Variable> variables;
	
	static {
		variables = new HashMap<String, Variable>();
	}
	
	public static void addVariable(String name, Variable variable) {
		if (variables.containsKey(name) || variables.containsValue(variable)) return;
		
		variables.put(name, variable);
	}
	
	public static Object getValue(String variableName) {
		if (!variables.containsKey(variableName)) return null;
		
		return variables.get(variableName);
	}
}
