package com.ancient.rpg.parameter;

public class Parameter {
	private ParameterType type;
	private String alias;
	private boolean array;
	
	public Parameter(ParameterType type, String alias, boolean array) {
		this.type = type;
		this.alias = alias;
		this.array = array;
	}
	
	public Parameter(ParameterType type, boolean array) {
		this.type = type;
		this.array = array;
		this.alias = "";
	}

	/** 
	 * @return The alias of this Parameter
	 */
	public String getAlias() {
		return this.alias;
	}
	
	/**
	 * @return The type of this Parameter as a ParameterType.
	 */
	public ParameterType getType() {
		return this.type;
	}
	
	/**
	 * @return true if it is an array, false otherwise
	 */
	public boolean isArray() {
		return this.array;
	}
}
