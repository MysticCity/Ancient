package com.ancientshores.Ancient.Display;

public enum Bar {
	XP("xpbar"),
	BOSS("bossbar"),
	NONE("none");
	
	private String label;
	
	private Bar(String label) {
		this.label = label;
	}
	
	public static Bar getByName(String name) {
		for (Bar b : Bar.values()) {
			if (b.label.equalsIgnoreCase(name))
				return b;
		}
		
		return NONE;
	}
	
	@Override
	public String toString() {
		return label.toUpperCase();
	}
}
