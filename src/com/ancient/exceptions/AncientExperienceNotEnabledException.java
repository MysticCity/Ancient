package com.ancient.exceptions;

public class AncientExperienceNotEnabledException extends Exception {
	private static final long serialVersionUID = 1L;

	public AncientExperienceNotEnabledException() {
		super("Ancient experience system is not enabled.");
	}
}
