package com.ancientshores.Ancient.Permissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;

import com.ancientshores.Ancient.Ancient;

public class Permissions {
	
	private static ArrayList<AncientPermission> permissions = new ArrayList<AncientPermission>();
	
	public static void registerPermissions() {
		loadPermissions();
		
		for (AncientPermission perm : permissions) {
			Bukkit.getPluginManager().addPermission(new Permission(perm.getName(), perm.getDescription(), perm.getDefaultValue(), perm.getChildrenAsMap()));
		}
	}

	@SuppressWarnings("unchecked")
	private static void loadPermissions() {
		if (permissions.size() == 0) {
			YamlConfiguration permConfig = YamlConfiguration.loadConfiguration(new File(Ancient.plugin.getDataFolder().getPath() + ".jar" + File.separator + "permissions.yml"));
		
			Set<String> perms = permConfig.getKeys(false);
			
			for (String perm : perms) {
				permissions.add(new AncientPermission((Map<String, Object>) permConfig.get(perm)));
			}
		}
	}
}
