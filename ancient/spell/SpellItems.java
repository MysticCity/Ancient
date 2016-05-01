package com.ancient.spell;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.ancientshores.Ancient.Ancient;

public class SpellItems {
	private static HashMap<String, String> items;
//	private static HashMap<>
	
	static {
		String packageName = "com.ancient.spell.item";
		
		packageName = packageName.replaceAll("\\." , "/");
		
		try{
			JarInputStream jarFile = new JarInputStream(new FileInputStream(Ancient.plugin.getDataFolder().getPath() + ".jar"));
			JarEntry jarEntry;
			
			while((jarEntry = jarFile.getNextJarEntry()) != null) {
				if((jarEntry.getName().startsWith(packageName)) && (jarEntry.getName().endsWith(".class")) ) {
					String className = jarEntry.getName().replaceAll("/", "\\.");
					items.put(className.split("\\.")[className.split("/").length - 1], className);
				}
			}
			jarFile.close();
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
