package com.ancient.rpg.stuff;

import java.util.ArrayList;

public class Splitter {
	public static String[] splitByArgument(String s) {
		ArrayList<String> arguments = new ArrayList<String>();
		int openBraces = 0, currentArgumentStartIndex = 0;
		boolean openQuotes = false;
		s = s.trim();
		
		if (s.indexOf("(") == 0) s = s.substring(1, s.length() - 1);
		
		s = s.trim();
		
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ',' && !openQuotes && openBraces == 0) {
				arguments.add((s.substring(currentArgumentStartIndex, i - 1)).trim());
				currentArgumentStartIndex = i + 1;
			}
			
			if ((c == '(') && !openQuotes) openBraces++;
			if ((c == ')') && !openQuotes) openBraces--;
			
			if (c == '"' && s.charAt(i - 1) != '$') openQuotes ^= true;
		}
		return (String[]) arguments.toArray();
	}
}
