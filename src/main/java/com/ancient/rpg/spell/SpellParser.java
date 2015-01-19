package com.ancient.rpg.spell;

import com.ancient.rpg.exceptions.AncientRPGIncorrectLineException;
import com.ancient.rpg.spell.datatypes.BooleanDataType;
import com.ancient.rpg.spell.datatypes.EntityDataType;
import com.ancient.rpg.spell.datatypes.LocationDataType;
import com.ancient.rpg.spell.datatypes.MaterialDataType;
import com.ancient.rpg.spell.datatypes.NumberDataType;
import com.ancient.rpg.spell.datatypes.PlayerDataType;
import com.ancient.rpg.spell.datatypes.StringDataType;
import com.ancient.rpg.spellmaker.Parameterizable;

public class SpellParser {

	private static String trimLine(String line) {
		
		String returnLine = line.trim();
		
		while (returnLine.charAt(0) == '(' && returnLine.charAt(returnLine.length() - 1) == ')') {
			returnLine = returnLine.substring(1, returnLine.length() - 2);
			returnLine = returnLine.trim();
		}
		
		if (!Character.isAlphabetic(returnLine.charAt(0))) {
			try {
				throw new AncientRPGIncorrectLineException(line);
			} catch (AncientRPGIncorrectLineException ex) {
				ex.printStackTrace();
			}
		}
		return returnLine;
	}
	
	public static SpellItem parseLine(String line, SpellSection section) {
		line = trimLine(line);
		line = line.trim();
		
		int commandStartIndex = 0, commandEndIndex = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ' || line.charAt(i) == ':') {
				commandEndIndex = i;
				break;
			}
		}
		
		String commandName = line.substring(commandStartIndex, commandEndIndex).trim();
		
		if (commandName == null || commandName.equalsIgnoreCase("")) {} // kein korrektes kommando
		
		if (Character.isLowerCase(commandName.charAt(0))) {
			if (commandName.equals("if")) {
				
			} else if (commandName.equals("while")) {
				
			} else if (commandName.equals("else")) {
				
			} else if (commandName.equals("for")) {
				
			} else if (commandName.equals("switch")) {
				
			} else if (commandName.equals("case")) {
				
			} else if (commandName.equals("var")) {
				
			} else if (commandName.equals("p")) { // player
				
			} else if (commandName.equals("e")) { // entity
				
			} else if (commandName.equals("s")) { // string
//				new StringDataType(line, description);
			} else if (commandName.equals("b")) { // boolean
				
			} else if (commandName.equals("l")) { // location
				
			} else if (commandName.equals("m")) { // material
				
			} else if (commandName.equals("n")) { // number
				
			}
			// if, else, while etc
			// check ob in spell. wenn in spellitem drin, dann geht das nicht...
			
			// kann auch var sein
			// wenn var was anderes
		}
		else {
			// Kommandos usw
			return parseCommand(commandName, line.substring(commandEndIndex + 1));
		}
		return null;
	}

	private static SpellItem parseCommand(String commandName, String arguments) {
		SpellItem item = null;
			try {
				item = (SpellItem) Class.forName(SpellItems.getFullName(commandName)).newInstance();
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		// wenn keine parameter übergeben parameterisierbar sein	
		if (arguments.equalsIgnoreCase("")) {
			if (item instanceof Parameterizable) {
				return null; // error, da eigentlich parameter da sein müssten
			}
		}
		else {
			if (!((Parameterizable) item).validParameters(arguments)) return null; // eigentlich müsste hier true kommen, wenn korrekt
		}
		
		return item;
	}
	
	public static boolean isCommand(String s) {
		s = trimLine(s);
		
		if (s.contains(":")) return true;
		
		String[] splitted = s.split(":");
		if (SpellItems.getFullName(splitted[0].trim()) != null) return true;
		
		return false;
	}
	
	public static SpellItem parse(String line, int lineNumber) {
		line = line.trim();
		char[] chars = line.toCharArray();
		int commandStart = 0, commandEnd = 0;
		
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == ':') {
				commandEnd = i;
				break;
			}
		}
		
		String command = line.substring(commandStart, commandEnd);
		String arguments = line.substring(commandEnd + 1);
		if (command == null || command == "") {} // ungültige zeile
		
		char startChar = chars[0];
		if (Character.isLowerCase(startChar)) {
			if (line.startsWith("var:")) { // variable differentiate call or comparison
				
			} else if (line.startsWith("while:")) { // while
				
			} else if (line.startsWith("for:")) { // for differentiate for each and for i < j
				
			} else if (line.startsWith("if:")) { // if
				
			} else if (line.startsWith("else:")) { // else after if
				
			} else if (line.startsWith("switch:")) { // switch
				
			} else if (line.startsWith("case:")) { // case in switch
				
			} else if (line.startsWith("p:")) { // Player needs uuid or method returning player
				return new PlayerDataType(lineNumber, arguments);
			} else if (line.startsWith("e:")) { // Entity needs uuid or method returning entity/player
				return new EntityDataType(lineNumber, arguments);
			} else if (line.startsWith("n:")) { // Number double or int or method
				return new NumberDataType(lineNumber, arguments);
			} else if (line.startsWith("s:")) { // String with ""
				return new StringDataType(lineNumber, arguments);
			} else if (line.startsWith("b:")) { // Boolean true/false or method
				return new BooleanDataType(lineNumber, arguments);
			} else if (line.startsWith("l:")) { // Location format: worldname, x, y, z or method returning parts or location
				return new LocationDataType(lineNumber, arguments);
			} else if (line.startsWith("m:")) { // Material name or method
				return new MaterialDataType(lineNumber, arguments);
			} else {
				// ung��ltige zeile
			}
			
		}
		return null;
	}
}
