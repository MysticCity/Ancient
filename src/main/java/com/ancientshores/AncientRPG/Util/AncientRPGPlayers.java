package com.ancientshores.AncientRPG.Util;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import com.ancientshores.AncientRPG.AncientRPG;

public class AncientRPGPlayers {
	private static HashMap<UUID, String> players = new HashMap<UUID, String>();
	
	public static void addPlayer(UUID uuid, String name) {
		if (players.containsKey(uuid)) return;
		
		players.put(uuid, name);
	}
	
	public static String getPlayerName(UUID uuid){
		if (!players.containsKey(uuid)) return "";
		
		return players.get(uuid);
	}
	
	public static void loadAllPlayers() {
		for (File f : new File(AncientRPG.plugin.getDataFolder().getPath() + "/players/").listFiles()) {
			if (!f.isFile()) {
				continue;
			}
			if (!f.getName().endsWith(".yml")) {
				continue;
			}
			UUID uuid = UUID.fromString(f.getName().replaceAll(".yml", ""));
			addPlayer(uuid, AncientRPGUUID.getName(uuid));
		}
	}
}
