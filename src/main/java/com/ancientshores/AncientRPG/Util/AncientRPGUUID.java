package com.ancientshores.AncientRPG.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;

public class AncientRPGUUID {
	private static Map<String, UUID> converted;
	
	public static void runConverter() {
		converted = new HashMap<String, UUID>();
		convert();
		
		inform();
	}
	
	private static void inform() {
		if (!AncientRPG.plugin.isEnabled()) {
			AncientRPG.plugin.getLogger().info("Converted 0 files.");
			AncientRPG.plugin.getLogger().warning("The convertation was canceled because an error occoured.");
			AncientRPG.plugin.getLogger().warning("The plugin cannot be used when these files are not converted.");
			AncientRPG.plugin.getLogger().warning("AncientRPG now disables...");
			return;
		}
		
		if (converted.size() == 0) {
			AncientRPG.plugin.getLogger().info("Converted 0 files.");
			AncientRPG.plugin.getLogger().info("Whether your files are uptodate, or you don't have any player files.");
		} else if (converted.size() == 1) {
			AncientRPG.plugin.getLogger().info("Converted 1 file.");
		}
		else {
			AncientRPG.plugin.getLogger().info(String.format("Converted %d files.", converted.size()));
			
		}
		
		AncientRPG.plugin.getLogger().info("All files that where in the folder \"plugins/AncientRPG/players\" should be converted.");
		AncientRPG.plugin.getLogger().info("If you find any unconverted files just reload the plugin/server and report that issue.");
		AncientRPG.plugin.getLogger().info("All player names were converted into UUID's.");
		AncientRPG.plugin.getLogger().info("The plugin now is ready for future use!");
	}

	private static void convert() {
		for (File f : new File(AncientRPG.plugin.getDataFolder().getPath() + "/players/").listFiles()) {
			if (!f.isFile()) {
				continue;
			}
			if (!f.getName().endsWith(".yml")) {
				continue;
			}
			String name = f.getName().replaceAll(".yml", "");
	
			try {
				UUID uuid = UUID.fromString(name);
				continue;
			} catch (IllegalArgumentException ex) {
				// Noch nicht convertiert
			}
			
			try {
				FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			} catch (IllegalArgumentException ex) {
				AncientRPG.plugin.getLogger().warning(String.format("Could not change %s's player configuration to the new UUID system. Please reload the server and try again if you want to use AncientRPG!", name));
				AncientRPG.plugin.getPluginLoader().disablePlugin(AncientRPG.plugin);
				ex.printStackTrace();
			}
			
			
//			UUID uuid = null;
//			
//			if (isConverted(config)) {
//				continue;
//			}
//			
//			for (String s : config.getKeys(true)) {
//				System.out.println(s + " :=-=:" + (config.get(s)));
//			}
//			
//			uuid = getUUID(config.getString("PlayerData.player"));
//			name = config.getString("PlayerData.player");
//			config.set("PlayerData.xpsystem.xpname", null);
//			config.set("PlayerData.hpsystem.playername", null);
//			config.set("PlayerData.manasystem.playername", null);
//			
//			config.set("PlayerData.player", uuid.toString());
//			
//			config.set("PlayerData.xpsystem.uuid", uuid.toString());
//			config.set("PlayerData.hpsystem.uuid", uuid.toString());
//			config.set("PlayerData.manasystem.uuid", uuid.toString());
//			
//			if (!isConverted(config)) {
//				break;
//			}	
			
//			try {
//				config.save(f);
//			} catch (IOException e) {
//				AncientRPG.plugin.getLogger().log(Level.WARNING, "Could not save " + name + "'s changed player configuration to the hard drive. Please reload the server and try again if you want to use AncientRPG!");
//				AncientRPG.plugin.getPluginLoader().disablePlugin(AncientRPG.plugin);
//				e.printStackTrace();
//				break;
//			}
			File dat = new File(f.getPath().replaceAll(f.getName(), name + ".dat"));
			f.renameTo(new File(f.getPath().replaceAll(f.getName(), converted.get(name) + ".yml")));
			dat.renameTo(new File(dat.getPath().replaceAll(dat.getName(), converted.get(name) + ".dat")));
			
		}
	}
	
//	private static boolean isConverted(FileConfiguration config) {
//		if (config.contains("PlayerData.xpsystem.xpname")) return false;
//		if (config.contains("PlayerData.hpsystem.playername")) return false;
//		if (config.contains("PlayerData.manasystem.playername")) return false;
//		
//		if (!config.contains("PlayerData.xpsystem.uuid")) return false;
//		if (!config.contains("PlayerData.hpsystem.uuid")) return false;
//		if (!config.contains("PlayerData.manasystem.uuid")) return false;
//	
//		try {
//			UUID.fromString(config.getString("PlayerData.player"));
//			UUID.fromString(config.getString("PlayerData.xpsystem.uuid"));
//			UUID.fromString(config.getString("PlayerData.hpsystem.uuid"));
//			UUID.fromString(config.getString("PlayerData.manasystem.uuid"));
//		}
//		catch (IllegalArgumentException ex) {
//			return false;
//		}
//		
//		return true;
//	}
	
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

	public static String getName(UUID uuid) {
		String name = "";
		InputStream is = null;
		
		try {
			is = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replaceAll("-", "")).openStream();
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
			
			name = (String) json.get("name");
			
			scanner.close();
			
			try {
				is.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}	
		}
		
		return name;
	}

}
