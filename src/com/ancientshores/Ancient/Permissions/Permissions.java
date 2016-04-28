package com.ancientshores.Ancient.Permissions;

import com.ancientshores.Ancient.Ancient;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

public class Permissions
{
  private static ArrayList<AncientPermission> permissions = new ArrayList();
  
  public static void registerPermissions()
  {
    
    for (AncientPermission perm : permissions) {
      Bukkit.getPluginManager().addPermission(new Permission(perm.getName(), perm.getDescription(), perm.getDefaultValue(), perm.getChildrenAsMap()));
    }
  }
  
  private static void loadPermissions()
  {
    YamlConfiguration permConfig;
    if (permissions.size() == 0)
    {
      permConfig = YamlConfiguration.loadConfiguration(new File(Ancient.plugin.getDataFolder().getPath() + ".jar" + File.separator + "permissions.yml"));
      
      Set<String> perms = permConfig.getKeys(false);
      for (String perm : perms) {
        permissions.add(new AncientPermission((Map)permConfig.get(perm)));
      }
    }
  }
}
