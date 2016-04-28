package com.ancientshores.Ancient.Permissions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.permissions.PermissionDefault;

public class AncientPermission
  implements ConfigurationSerializable
{
  private String name;
  private String description;
  private PermissionDefault defaultValue;
  private List<AncientPermission> children;
  
  public Map<String, Object> serialize()
  {
    HashMap<String, Object> map = new HashMap();
    map.put("name", this.name);
    map.put("description", this.description);
    map.put("default", this.defaultValue);
    map.put("children", this.children);
    return map;
  }
  
  public AncientPermission(Map<String, Object> map)
  {
    this.name = ((String)map.get("name"));
    this.description = ((String)map.get("description"));
    this.defaultValue = ((PermissionDefault)map.get("default"));
    this.children = ((List)map.get("children"));
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public PermissionDefault getDefaultValue()
  {
    return this.defaultValue;
  }
  
  public Boolean getDefaultValueAsBoolean()
  {
    switch (this.defaultValue)
    {
    case FALSE: 
    case NOT_OP: 
    case OP: 
      return Boolean.valueOf(false);
    case TRUE: 
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
  
  public Map<String, Boolean> getChildrenAsMap()
  {
    Map<String, Boolean> map = new HashMap();
    for (AncientPermission perm : this.children) {
      map.put(perm.getName(), perm.getDefaultValueAsBoolean());
    }
    return map;
  }
  
  public List<AncientPermission> getChildren()
  {
    return this.children;
  }
}
