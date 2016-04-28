package com.ancient.spell.datatypes;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.DataType;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.spellmaker.Returnable;
import com.ancient.stuff.Splitter;

public class LocationDataType extends DataType<Location> {
	private Location value;
	private Returnable<String> world;
	private Returnable<Number> x;
	private Returnable<Number> y;
	private Returnable<Number> z;
	
	@SuppressWarnings("unchecked")
	public LocationDataType(int line, String value) {
		super(line, "<html>A location data type, which can store a <b>location</b> consisting of a <b>world name</b> and the <b>x-</b>, <b>y-</b> and <b>z-coordinates</b> as <b>numbers</b>.</html>");
		
		String[] splitted = Splitter.splitByArgument(value);
		if (splitted.length != 4) {} // error ungültige länge
		
		SpellItem world = SpellParser.parse(splitted[0], line);
		SpellItem x = SpellParser.parse(splitted[1], line);
		SpellItem y = SpellParser.parse(splitted[2], line);
		SpellItem z = SpellParser.parse(splitted[3], line);
		
		if (world instanceof Returnable && x instanceof Returnable && y instanceof Returnable && z instanceof Returnable) {
			this.world = (Returnable<String>) world;
			this.x = (Returnable<Number>) x;
			this.y = (Returnable<Number>) y;
			this.z = (Returnable<Number>) z;
		}
		else {} // error
	}

	@Override
	public Location getValue() {
		if (this.world != null) calculateReturn(); // ein check reicht, da wenn alle konvertiert wurden und somit entweder alle oder keins null ist
		return this.value;
	}

	private void calculateReturn() {
		this.value = new Location(Bukkit.getWorld(this.world.getValue()), this.x.getValue().doubleValue(), this.y.getValue().doubleValue(), this.z.getValue().doubleValue());
	}

	@Override
	public Parameter getReturnType() {
		return new Parameter(ParameterType.LOCATION, false);
	}
}
