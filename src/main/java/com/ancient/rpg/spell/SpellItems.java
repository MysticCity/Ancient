package com.ancient.rpg.spell;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.ancientshores.AncientRPG.AncientRPG;

public class SpellItems {
	private static HashMap<String, String> items;
	
	static {
		String packageName = "com.ancient.rpg.spell.item";
		
		packageName = packageName.replaceAll("\\." , "/");
		
		try{
			JarInputStream jarFile = new JarInputStream(new FileInputStream(AncientRPG.plugin.getDataFolder().getPath() + ".jar"));
			JarEntry jarEntry;
			
			while((jarEntry = jarFile.getNextJarEntry()) != null) {
				if((jarEntry.getName().startsWith(packageName)) && (jarEntry.getName().endsWith(".class")) ) {
					String className = jarEntry.getName().replaceAll("/", "\\.");
					items.put(className.split("\\.")[className.split("/").length - 1], className);
				}
			}
		}
		catch( Exception e){
			e.printStackTrace ();
		}
	}
	
	public static String getFullName(String s) {
		if (items.containsKey(s)) return items.get(s);
		
		return null;
	}
	
}
