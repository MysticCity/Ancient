
package com.ancientreborn.Ancient.Dependencies;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class DependencyNotFoundEvent extends Event
{

    private final Plugin dependency;
    private final DependencyManager.DependencyType type;
    private final String dependencyName;
    
    //Construction
    public DependencyNotFoundEvent( Plugin dependency, DependencyManager.DependencyType type )
    {
        
        this.dependency = dependency;
        
        this.type = type;
        
        this.dependencyName = dependency.getName();
        
    }
    
    //Construction
    public DependencyNotFoundEvent( String dependency, DependencyManager.DependencyType type )
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
    public DependencyManager.DependencyType getDependencyType()
    {
        return this.type;
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
