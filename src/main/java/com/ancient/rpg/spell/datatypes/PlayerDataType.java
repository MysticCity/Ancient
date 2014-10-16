package com.ancient.rpg.spell.datatypes;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spell.DataType;
import com.ancient.rpg.spell.SpellItem;
import com.ancient.rpg.spell.SpellParser;
import com.ancient.rpg.spellmaker.Returnable;

public class PlayerDataType extends DataType<Player> {
	private Player value;
	private Returnable<Player> valueItem;
	
	@SuppressWarnings("unchecked")
	public PlayerDataType(int line, String value) {
		super(line, "<html>A player data type, which can store a <b>player</b>.</html>");
		
		try {
			this.value = Bukkit.getPlayer(UUID.fromString(value));
			
			if (this.value == null) {} // gibt keinen spieler online mit der uuid...
		} catch (NumberFormatException ex) {
			SpellItem item = SpellParser.parse(value, line);
			if (item instanceof Returnable) this.valueItem = (Returnable<Player>) item;
			else {} // exception. kann nicht verwendet werden.
		}
	}

	@Override
	public Player getValue() {
		if (this.valueItem != null) calculateReturn();
		
		return this.value;
	}

	private void calculateReturn() {
		this.value = this.valueItem.getValue();
	}

	@Override
	public Parameter getReturnType() {
		return new Parameter(ParameterType.PLAYER, false);
	}

}
