package com.ancientshores.AncientRPG.Permissions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.permissions.PermissionDefault;

public class AncientRPGPermission implements ConfigurationSerializable {

	/**
	 * The name of the permission
	 */
	private String name;
	
	/**
	 * The desription of the permission
	 */
	private String description;
	
	/**
	 * The default value of the permission
	 * 
	 * Defines if it should be standard or not for players/operators.
	 */
	private PermissionDefault defaultValue;
	
	/**
	 * A map with all Children of this permisson
	 * 
	 * Stored in a list.
	 */
	private List<AncientRPGPermission> children;
		
	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", this.name);
		map.put("description", this.description);
		map.put("default", this.defaultValue);
		map.put("children", this.children);
		return map;
	}

	@SuppressWarnings("unchecked")
	public AncientRPGPermission(Map<String, Object> map) {
		this.name = (String) map.get("name");
		this.description = (String) map.get("description");
		this.defaultValue = (PermissionDefault) map.get("default");
		this.children = (List<AncientRPGPermission>) map.get("children");
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	
	/**
	 * @return the defaultValue
	 */
	public PermissionDefault getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @return the defaultValue parsed to a boolean. Only returns true if defaultValue equals TRUE
	 */
	public Boolean getDefaultValueAsBoolean() {
		switch (defaultValue) {
		case FALSE: case NOT_OP: case OP:
			return false;
			
		case TRUE:
			return true;
		}
		
		return false;
	}

	/**
	 * @return the children parsed to a map with defaultValue parsed to boolean
	 */
	public Map<String, Boolean> getChildrenAsMap() {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for (AncientRPGPermission perm : children) {
			map.put(perm.getName(), perm.getDefaultValueAsBoolean());
		}
		return map;
	}
	
	/**
	 * @return the children parsed to a map with defaultValue parsed to boolean
	 */
	public List<AncientRPGPermission> getChildren() {
		return children;
	}
}
