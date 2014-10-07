package com.ancient.rpg.parameter;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

public enum ParameterType {
	PLAYER(UUID.class),
	NUMBER(Number.class),
	STRING(String.class),
	BOOLEAN(Boolean.class),
	LOCATION(Location.class),
	MATERIAL(Material.class),
	ENTITY(UUID.class);
	
	private Class<?> clazz;
	private <T> ParameterType(Class<T> c) {}
	
	public Class<?> getClassType() {
		return this.clazz;
	}
}
