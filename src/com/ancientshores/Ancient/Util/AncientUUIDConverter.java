package com.ancientshores.Ancient.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ancientshores.Ancient.Ancient;

public class AncientUUIDConverter {
	private static Map<String, UUID> converted;
	
	public static void runConverter() {
		converted = new HashMap<String, UUID>();
		convert();
		
		inform();
	}
	
	private static void inform() {
		if (!Ancient.plugin.isEnabled()) {
			Ancient.plugin.getLogger().info("Converted 0 files.");
			Ancient.plugin.getLogger().warning("The convertation was canceled because an error occoured.");
			Ancient.plugin.getLogger().warning("The plugin cannot be used when these files are not converted.");
			Ancient.plugin.getLogger().warning("Ancient now disables...");
			return;
		}
		
		if (converted.size() == 0) {
			Ancient.plugin.getLogger().info("Converted 0 files.");
			Ancient.plugin.getLogger().info("Whether your files are uptodate, or you don't have any player files.");
		} else if (converted.size() == 1) {
			Ancient.plugin.getLogger().info("Converted 1 file.");
		}
		else {
			Ancient.plugin.getLogger().info(String.format("Converted %d files.", converted.size()));
			
		}
		
		Ancient.plugin.getLogger().info("All files that where in the folder \"plugins/Ancient/players\" should be converted.");
		Ancient.plugin.getLogger().info("If you find any unconverted files just reload the plugin/server and report that issue.");
		Ancient.plugin.getLogger().info("All player names were converted into UUID's.");
		Ancient.plugin.getLogger().info("The plugin now is ready for future use!");
	}

	private static void convert() {
		File folder = new File(Ancient.plugin.getDataFolder().getPath() + "/players/");
		
		if (!folder.exists()) return;
		
		for (File f : folder.listFiles()) {
			if (!f.isFile()) {
				continue;
			}
			if (!f.getName().endsWith(".yml")) {
				continue;
			}
			String name = f.getName().replaceAll(".yml", "");
	
			try {
				@SuppressWarnings("unused")
				UUID uuid = UUID.fromString(name);
				continue;
			} catch (IllegalArgumentException ex) {
				// Noch nicht convertiert
			}
			
			try {
				@SuppressWarnings("unused")
				FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			} catch (IllegalArgumentException ex) {
				Ancient.plugin.getLogger().warning(String.format("Could not change %s's player configuration to the new UUID system. Please reload the server and try again if you want to use Ancient!", name));
				Ancient.plugin.getPluginLoader().disablePlugin(Ancient.plugin);
				ex.printStackTrace();
			}
			
			File dat = new File(f.getPath().replaceAll(f.getName(), name + ".dat"));
			f.renameTo(new File(f.getPath().replaceAll(f.getName(), converted.get(name) + ".yml")));
			dat.renameTo(new File(dat.getPath().replaceAll(dat.getName(), converted.get(name) + ".dat")));
			
		}
	}
	
	public static UUID getUUID(String name) {
		if (converted.containsKey(name)) return converted.get(name);
		
		UUID uuid = null;
		InputStream is = null;
		
		try {
			is = new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		if (is != null) {
			Scanner scanner = new Scanner(is, "UTF-8");
			String jsonString = scanner.next();
			JSONParser jsonParser = new JSONParser();
			JSONObject json = null;
			
			try {
				json = (JSONObject) jsonParser.parse(jsonString);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			
			String uuidDashed = (String) json.get("id");
			uuidDashed = uuidDashed.substring(0 ,8) + "-" + uuidDashed.substring(8, 12) + "-" + uuidDashed.substring(12, 16) + "-" + uuidDashed.substring(16, 20) + "-" + uuidDashed.substring(20, uuidDashed.length());
			
			uuid = UUID.fromString(uuidDashed);
			
			scanner.close();
			
			try {
				is.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}	
		}
		
		converted.put(name, uuid);
		return uuid;
	}

}
