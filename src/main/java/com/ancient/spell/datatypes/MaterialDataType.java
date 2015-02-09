package com.ancient.spell.datatypes;

import org.bukkit.Material;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.DataType;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.spellmaker.Returnable;

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
