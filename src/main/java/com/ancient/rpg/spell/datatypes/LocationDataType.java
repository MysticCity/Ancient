package com.ancient.rpg.spell.datatypes;

import org.bukkit.Location;

import com.ancient.rpg.spell.DataType;
import com.ancient.rpg.stuff.Splitter;

public class LocationDataType extends DataType<Location> {
	private Location value;
	
	public LocationDataType(int line, String value) {
		super(line, "<html>A location data type, which can store a <b>location</b> consisting of a <b>world name</b> and the <b>x-</b>, <b>y-</b> and <b>z-coordinates</b> as <b>numbers</b>.</html>");
		
		String[] splitted = Splitter.splitByArgument(value);
		
		this.value = value;
	}

	@Override
	public Location getValue() {
		return this.value;
	}
}
