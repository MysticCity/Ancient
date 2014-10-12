package com.ancient.rpg.spell;

import com.ancient.rpg.exceptions.AncientRPGIncorrectLineException;
import com.ancient.rpg.spell.datatypes.StringDataType;
import com.ancient.rpg.spellmaker.Parameterizable;
import com.ancient.rpg.stuff.Splitter;

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
				new StringDataType(line, description)
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
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
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
			if (line.startsWith("var:")) {
			
			} else if (line.startsWith("if:")) {
				
			} else if (line.startsWith("while:")) {
				
			} else if (line.startsWith("for:")) {
				
			} else if (line.startsWith("if:")) {
				
			} else if (line.startsWith("else")) {
				
			} else if (line.startsWith("switch:")) {
				
			} else if (line.startsWith("case:")) {
				
			} else if (line.startsWith("p:")) {
				
			} else if (line.startsWith("e:")) {
				
			} else if (line.startsWith("n:")) {
				
			} else if (line.startsWith("s:")) { // könnte sonst für switch gehalten werden
				return new StringDataType(lineNumber, arguments);
			} else if (line.startsWith("b:")) {
				
			} else if (line.startsWith("l:")) {
				
			} else if (line.startsWith("m:")) {
				
			} else {
				// ungültige linie
			}
			
		}
		return null;
	}
}
