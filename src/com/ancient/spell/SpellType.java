package com.ancient.spell;

public enum SpellType {
	ACTIVE,
	PASSIVE,
	BUFF,
	COMMAND;
	
	public static SpellType getByName(String s) {
		if (s.equalsIgnoreCase("active")) return ACTIVE;
		if (s.equalsIgnoreCase("passive")) return PASSIVE;
		if (s.equalsIgnoreCase("buff")) return BUFF;
		if (s.equalsIgnoreCase("command")) return COMMAND;
	
		return null;
	}
}
