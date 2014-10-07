package com.ancientshores.AncientRPG.Permissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;

import com.ancientshores.AncientRPG.AncientRPG;

public class Permissions {
	
	private static ArrayList<AncientRPGPermission> permissions = new ArrayList<AncientRPGPermission>();
	
	public static void registerPermissions() {
		loadPermissions();
		
		for (AncientRPGPermission perm : permissions) {
			Bukkit.getPluginManager().addPermission(new Permission(perm.getName(), perm.getDescription(), perm.getDefaultValue(), perm.getChildrenAsMap()));
		}
	}

	@SuppressWarnings("unchecked")
	private static void loadPermissions() {
		if (permissions.size() == 0) {
			YamlConfiguration permConfig = YamlConfiguration.loadConfiguration(new File(AncientRPG.plugin.getDataFolder().getPath() + ".jar" + File.separator + "permissions.yml"));
		
			Set<String> perms = permConfig.getKeys(false);
			
			for (String perm : perms) {
				permissions.add(new AncientRPGPermission((Map<String, Object>) permConfig.get(perm)));
			}
		}
	}
}
