package com.ancient.rpg.spell.datatypes;

import org.bukkit.Material;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spell.DataType;
import com.ancient.rpg.spell.SpellItem;
import com.ancient.rpg.spell.SpellParser;
import com.ancient.rpg.spellmaker.Returnable;

public class MaterialDataType extends DataType<Material> {
	private Material value;
	private Returnable<Material> valueItem;
	
	@SuppressWarnings("unchecked")
	public MaterialDataType(int line, String value) {
		super(line, "<html>A material data type, which can store a <b>material</b>.</html>");
		
		if (value.toUpperCase().equalsIgnoreCase(value)) {
			this.value = Material.getMaterial(value);
		}
		else {
			SpellItem item = SpellParser.parse(value, line);
		
			if (item instanceof Returnable) this.valueItem = (Returnable<Material>) item;
			else {} // exception. kann nicht verwendet werden.
		}
	}
	
	@Override
	public Material getValue() {
		if (this.valueItem != null) calculateReturn();
		
		return this.value;
	}

	private void calculateReturn() {
		this.value = this.valueItem.getValue();
	}

	@Override
	public Parameter getReturnType() {
		return new Parameter(ParameterType.MATERIAL, false);
	}
}
