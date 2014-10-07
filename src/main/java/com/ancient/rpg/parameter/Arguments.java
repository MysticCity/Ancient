package com.ancient.rpg.parameter;

import java.util.List;

public class Arguments {
	private List<Object> values;
	
	private Spell spell; // Spellname, SpellInformation, spieler || NICHT importieren. muss neu erstellt werden
	
	public Arguments(Spell spell, List<Object> values) {
		this.spell = spell;
		this.values = values;
	}
	
	/**
	 * @return the values
	 */
	public List<Object> getValues() {
		return values;
	}
	
	public Spell getSpell() {
		return this.spell;
	}

}
