package com.ancient.rpg.spell.datatypes;

import org.bukkit.Location;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spell.DataType;
import com.ancient.rpg.spell.SpellItem;
import com.ancient.rpg.spell.SpellParser;
import com.ancient.rpg.stuff.Splitter;

public class LocationDataType extends DataType<Location> {
	private Location value;
	
	public LocationDataType(int line, String value) {
		super(line, "<html>A location data type, which can store a <b>location</b> consisting of a <b>world name</b> and the <b>x-</b>, <b>y-</b> and <b>z-coordinates</b> as <b>numbers</b>.</html>");
		
		String[] splitted = Splitter.splitByArgument(value);
		if (splitted.length != 4) {} // error ungültige länge
		
		SpellItem world = SpellParser.parse(splitted[0], line);
		SpellItem x = SpellParser.parse(splitted[1], line);
		SpellItem y = SpellParser.parse(splitted[2], line);
		SpellItem z = SpellParser.parse(splitted[3], line);
		
		this.value = value;
	}

	@Override
	public Location getValue() {
		return this.value;
	}

	@Override
	public Parameter getReturnType() {
		return new Parameter(ParameterType.LOCATION, false);
	}
}
