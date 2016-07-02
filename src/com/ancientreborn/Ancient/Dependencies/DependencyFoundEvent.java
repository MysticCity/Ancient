package com.ancientreborn.Ancient.Dependencies;

import com.ancientreborn.Ancient.Dependencies.DependencyManager.DependencyType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class DependencyFoundEvent extends Event
{
    
    private final Plugin dependency;
    private final DependencyType type;
    private final String dependencyName;
    
    //Construction
    public DependencyFoundEvent( Plugin dependency, DependencyType type )
    {
        
        this.dependency = dependency;
        
        this.type = type;
        
        this.dependencyName = dependency.getName();
        
    }
    
    //Construction
    public DependencyFoundEvent( String dependency, DependencyType type )
    {
        
        this.dependency = null;
        
        this.type = type;
        
        this.dependencyName = dependency;
        
    }
    
    //Get dependency
    public Plugin getDependency()
    {
        return this.dependency;
    }
    
    //Get dependency type
    public DependencyType getDependencyType()
    {
        return this.type;
    }
    
    //Get dependency name
    public String getName()
    {
        return this.dependencyName;
    }
    
    //Get handlers (not required)
    @Override
    public HandlerList getHandlers() 
    {
      return new HandlerList();
    }
    
    public static HandlerList getHandlerList() {
            return new HandlerList();
    }
    
}
