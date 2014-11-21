package com.ancient.rpg.spellmaker;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.spell.ExecutableSpellItem;
import com.ancient.rpg.spell.SpellItem;
import com.ancient.rpg.spell.SpellParser;
import com.ancient.rpg.stuff.Splitter;

public abstract class Parameterizable extends ExecutableSpellItem {
	protected Parameter[] parameterTypes;
	protected Object[] parameterValues;
	
	public Parameterizable(int line, String description, Parameter[] parameter) {
		super(line, description);
		
		this.parameterTypes = parameter;
		this.parameterValues = new Object[this.parameterTypes.length];
	}
	
	/** validValues is called when executing the spell.
	 *  It checks if the variables contain correct values and anything else.
	 * 
	 * @return true if all values that are passed at runtime are valid, false otherwise
	 */
	public boolean validValues() {
		// zu wenig/viel argumente
		if (this.parameterValues.length != this.parameterTypes.length) return false;
		
		for (int i = 0; i < this.parameterTypes.length; i++) {
			if (this.parameterValues[i] == null) return false;
			
			if (this.parameterValues[i] instanceof Object[]) {
				// array
			}
			else {
				// kein array
			}
			// überprüfen ob nciht null, gucken ob array oder nicht. wenn typ spellitem returntype überprüfen und bekommen.
		}
		// TODO alles nochmal, damit korrekt gecheckt wird
		
		for (int i = 0; i < this.parameterTypes.length; i++) {
			if (parameterValues[i] == null) return false;
			if (!parameterValues[i].getClass().equals(parameterTypes[i].getType().getClassType())) return false;
			if (parameterTypes[i].isArray()) {
				if (!(parameterValues[i] instanceof Object[])) return false;
				for (Object o : parameterValues) {
					if (o == null) return false;
				}
			}
			
		}
		return true;
	}
	
	/** validParameters is called while parsing a spell. 
	 * it checks if the in-spell used parameters are valid.
	 * 
	 * @param s the parameters written in the spell
	 * @return true if the given parameters are valid, false otherwise
	 */
	public boolean validParameters(String s) {
		ArrayList<Object> args = new ArrayList<Object>();
		String[] arguments = Splitter.splitByArgument(s);
		
		if (arguments.length != this.parameterTypes.length) return false;
		
		for (int i = 0; i < arguments.length; i++) {
			String argument = arguments[i].trim();
			if (argument.startsWith("(")) { // wenn methode 
				if (argument.indexOf(":") == -1) { // methode wie if, while etc
					
				}
				else { // methode mit oder ohne parameter
					
				}
				SpellItem item = SpellParser.parseLine(argument, this);
				
				if (!(item instanceof Returnable)) return false; // hat keinen return wert, kann also kein parameter sein.
				
				Parameter returning = ((Returnable<?>) item).getReturnType();
				
				if (parameterTypes[i].isArray() && returning.isArray() && parameterTypes[i].getType().compareTo(returning.getType()) == 0) args.add(i, item);
				else return false;
				
				
//				argument, dass aus argumenten besteht. wieder valid parameters. dafür wieder spellitem erstellen aus name
//				dass wieder durchchecken. desweiteren dann argumentitem.getreturntype == parameter[i]. wenn das alles okay true sonst false
			}
			else { // keine methode
				if (argument.startsWith("[")) { // wenn array
					@SuppressWarnings("unused")
					String[] subArguments = Splitter.splitArray(argument);
					
				}
				else { // normaler wert,
					
				}
			}
			
			if (!parameterTypes[i].isArray()) {
				switch (parameterTypes[i].getType()) {
				case BOOLEAN:
					if (argument.equals("true") || argument.equals("false")) {
						args.add(i, Boolean.parseBoolean(argument));
						break; // korrekt
					}
					return false; // ungültig. weder true noch false
				case ENTITY: case PLAYER:
					UUID uuid;
					try {
						uuid = UUID.fromString(argument);
					} catch (IllegalArgumentException ex) {
						return false;
					}
					args.add(i, uuid);
					break;
				case LOCATION:
					String[] subargs = argument.split(",");
					World w;
					double x, y, z;
					
					if (subargs.length != 4) return false;
					if ((w = Bukkit.getWorld(subargs[0].trim())) == null) return false;
					
					try {
						x = Double.parseDouble(subargs[1]);
						y = Double.parseDouble(subargs[2]);
						z = Double.parseDouble(subargs[3]);
					} catch (NumberFormatException ex) {
						return false;
					} catch (NullPointerException ex) {
						return false;
					}
					args.add(i, new Location(w, x, y, z));
					break;
				case MATERIAL:
					Material mat = Material.getMaterial(argument);
					if (mat == null) return false;
					
					args.add(i, mat);
					break;
				case NUMBER:
					try {
						Double.parseDouble(argument);
					} catch (NumberFormatException ex) {
						return false;
					} catch (NullPointerException ex) {
						return false;
					}
					args.add(i, Double.parseDouble(argument));
					break;
				case STRING:
					args.add(i, argument);
					break;
				}
			}
			else {
				String[] subargs = Splitter.splitByArgument(argument);
				
				switch (parameterTypes[i].getType()) {
				case BOOLEAN:
					boolean[] booleans = new boolean[subargs.length];
					for (int j = 0; j < subargs.length; i++) {
						String subarg = subargs[j];
						if (!(subarg.equals("true") || subarg.equals("false"))) return false;  // ungültig. weder true noch false
							
						booleans[j] = Boolean.parseBoolean(subarg);
					}
					args.add(i, booleans);
					break; // korrekt
				case ENTITY: case PLAYER:
					UUID[] uuids = new UUID[subargs.length];
					for (int j = 0; j < subargs.length; i++) {
						String subarg = subargs[j];
						try {
							uuids[j] = UUID.fromString(subarg);
						} catch (IllegalArgumentException ex) {
							return false;
						}	
					}
					
					args.add(i, uuids);
					break;
				case LOCATION:
					Location[] locations = new Location[subargs.length];
					for (int j = 0; j < subargs.length; i++) {
						String subarg = subargs[j];
						String[] locargs = Splitter.splitByArgument(subarg);
						World w;
						double x, y, z;
						
						if (locargs.length != 4) return false;
						if ((w = Bukkit.getWorld(locargs[0].trim())) == null) return false;
						
						try {
							x = Double.parseDouble(locargs[1]);
							y = Double.parseDouble(locargs[2]);
							z = Double.parseDouble(locargs[3]);
						} catch (NumberFormatException ex) {
							return false;
						} catch (NullPointerException ex) {
							return false;
						}
						
						locations[j] = new Location(w, x, y, z);
					}
					
					args.add(i, locations);
					break;
				case MATERIAL:
					Material[] materials = new Material[subargs.length];
					for (int j = 0; j < subargs.length; i++) {
						String subarg = subargs[j];
						Material mat = Material.getMaterial(subarg);
						if (mat == null) return false;
						
						materials[i] = mat;
					}
				
					args.add(i, materials);
					break;
				case NUMBER:
					Double[] doubles = new Double[subargs.length];
					for (int j = 0; j < subargs.length; i++) {
						String subarg = subargs[j];
						try {
							doubles[j] = Double.parseDouble(subarg);
						} catch (NumberFormatException ex) {
							return false;
						} catch (NullPointerException ex) {
							return false;
						}
					}
				
					args.add(i, doubles);
					break;
				case STRING:
					args.add(i, subargs);
					break;
				}
			}
		}
		
		parameterValues = args.toArray();
		return true;
	}
}
