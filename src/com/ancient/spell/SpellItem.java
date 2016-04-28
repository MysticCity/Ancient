package com.ancient.spell;

public abstract class SpellItem implements SpellSection {
	protected String description;
	protected int line;
	protected Spell spell;
	
	public SpellItem(/*Spell spell, */int line, String description) {
		this.spell = null; // = spell
		this.line = line;
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getLine() {
		return this.line;
	}
	
}
