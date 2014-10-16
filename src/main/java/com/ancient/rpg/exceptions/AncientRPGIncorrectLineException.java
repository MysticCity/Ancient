package com.ancient.rpg.exceptions;

public class AncientRPGIncorrectLineException extends Exception {

	private static final long serialVersionUID = 1L;

	public AncientRPGIncorrectLineException(String string) {
		super("Incorrect line: " + string + "\nThe spell can't be parsed.");
	}

}
