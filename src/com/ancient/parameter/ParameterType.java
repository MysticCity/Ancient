package com.ancient.parameter;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;

public enum ParameterType
{
  PLAYER(UUID.class),  NUMBER(Number.class),  STRING(String.class),  BOOLEAN(Boolean.class),  LOCATION(Location.class),  MATERIAL(Material.class),  ENTITY(UUID.class);
  
  private Class<?> clazz;
  
  private <T> ParameterType(Class<T> c) {}
  
  public Class<?> getClassType()
  {
    return this.clazz;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\parameter\ParameterType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */