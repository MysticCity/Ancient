package com.ancient.exceptions;

public class AncientVariableAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public AncientVariableAlreadyExistsException(String spellName, String className, int line, String varName) {
		super(className + " in spell " + spellName + " in line " + line + " wanted to create an variable called " + varName + ", that already exists.");
	}
}
