package com.ancientshores.AncientRPG.UUID.Converter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ancientshores.AncientRPG.AncientRPG;

public class AncientRPGUUIDConverter {
	private static int convertedCount;
	
	public static void runConverter() {
		convert();
		
		inform();
	}
	
	private static void inform() {
		if (!AncientRPG.plugin.isEnabled()) {
			AncientRPG.plugin.getLogger().info(String.format("[%s] Converted 0 files.", AncientRPG.plugin.getName()));
			AncientRPG.plugin.getLogger().warning(String.format("[%s] The convertation was canceled because an error occoured.", AncientRPG.plugin.getName()));
			AncientRPG.plugin.getLogger().warning(String.format("[%s] The plugin cannot be used when these files are not converted.", AncientRPG.plugin.getName()));
			AncientRPG.plugin.getLogger().warning(String.format("[%s] AncientRPG now disables...", AncientRPG.plugin.getName()));
			return;
		}
		
		if (convertedCount == 0) {
			AncientRPG.plugin.getLogger().info(String.format("[%s] Converted 0 files.", AncientRPG.plugin.getName()));
			AncientRPG.plugin.getLogger().info(String.format("[%s] Whether your files are uptodate, or you don't have any player files.", AncientRPG.plugin.getName()));
		} else if (convertedCount == 1) {
			AncientRPG.plugin.getLogger().info(String.format("[%s] Converted 1 file.", AncientRPG.plugin.getName()));
		}
		else {
			AncientRPG.plugin.getLogger().info(String.format("[%s] Converted %d files.", AncientRPG.plugin.getName(), convertedCount));
			
		}
		
		AncientRPG.plugin.getLogger().info(String.format("[%s] All files that where in the folder \"plugins/AncientRPG/players\" should be converted.", AncientRPG.plugin.getName()));
		AncientRPG.plugin.getLogger().info(String.format("[%s] If you find any unconverted files just reload the plugin/server and report that issue.", AncientRPG.plugin.getName()));
		AncientRPG.plugin.getLogger().info(String.format("[%s] All player names were converted into UUID's.", AncientRPG.plugin.getName()));
		AncientRPG.plugin.getLogger().info(String.format("[%s] The plugin now is ready for future use!", AncientRPG.plugin.getName()));
	}

	private static void convert() {
		convertedCount = 0;
		for (File f : new File(AncientRPG.plugin.getDataFolder().getPath() + "/players/").listFiles()) {
			if (!f.isFile()) {
				continue;
			}
			if (!f.getName().endsWith(".yml")) {
				continue;
			}
			
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			UUID uuid = null;
			String name = null;
			
			if (isConverted(config)) {
				continue;
			}
			
			uuid = getUUID(config.getString("PlayerData.player"));
			name = (String) config.get("PlayerData.player");
			config.set("PlayerData.xpsystem.xpname", null);
			config.set("PlayerData.hpsystem.playername", null);
			config.set("PlayerData.manasystem.playername", null);
			
			config.set("PlayerData.player", uuid.toString());
			
			config.set("PlayerData.xpsystem.uuid", uuid.toString());
			config.set("PlayerData.hpsystem.uuid", uuid.toString());
			config.set("PlayerData.manasystem.uuid", uuid.toString());
			
			if (!isConverted(config)) {
				AncientRPG.plugin.getLogger().log(Level.WARNING, "Could not change " + name + "'s player configuration to the new UUID system. Please reload the server and try again if you want to use AncientRPG!");
				AncientRPG.plugin.getPluginLoader().disablePlugin(AncientRPG.plugin);
				break;
			}	
			
			try {
				config.save(f);
			} catch (IOException e) {
				AncientRPG.plugin.getLogger().log(Level.WARNING, "Could not save " + name + "'s changed player configuration to the hard drive. Please reload the server and try again if you want to use AncientRPG!");
				AncientRPG.plugin.getPluginLoader().disablePlugin(AncientRPG.plugin);
				e.printStackTrace();
				break;
			}
			File dat = new File(f.getPath().replaceAll(f.getName(), f.getName().replaceAll(".yml", ".dat")));
			f.renameTo(new File(f.getPath().replaceAll(f.getName(), f.getName().replaceAll(name, uuid.toString()))));
			dat.renameTo(new File(dat.getPath().replaceAll(dat.getName(), dat.getName().replaceAll(name, uuid.toString()))));
			
			convertedCount++;
		}
	}
	
	private static boolean isConverted(FileConfiguration config) {
		if (config.contains("PlayerData.xpsystem.xpname")) return false;
		if (config.contains("PlayerData.hpsystem.playername")) return false;
		if (config.contains("PlayerData.manasystem.playername")) return false;
		
		if (!config.contains("PlayerData.xpsystem.uuid")) return false;
		if (!config.contains("PlayerData.hpsystem.uuid")) return false;
		if (!config.contains("PlayerData.manasystem.uuid")) return false;
	
		try {
			UUID.fromString(config.getString("PlayerData.player"));
			UUID.fromString(config.getString("PlayerData.xpsystem.uuid"));
			UUID.fromString(config.getString("PlayerData.hpsystem.uuid"));
			UUID.fromString(config.getString("PlayerData.manasystem.uuid"));
		}
		catch (IllegalArgumentException ex) {
			return false;
		}
		
		return true;
	}
	
	private static UUID getUUID(String name) {
		UUID uuid = null;
		InputStream is = null;
		
		try {
			is = new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection().getInputStream();
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
		return uuid;
	}

}
