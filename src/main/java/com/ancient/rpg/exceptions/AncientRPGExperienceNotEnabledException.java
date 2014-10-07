package com.ancient.rpg.exceptions;

public class AncientRPGExperienceNotEnabledException extends Exception {
	private static final long serialVersionUID = 1L;

	public AncientRPGExperienceNotEnabledException() {
		super("AncientRPG experience system is not enabled.");
	}
}
