package com.ancientreborn.Ancient.Dependencies;

import com.ancientreborn.Ancient.Ancient;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class DependencyManager 
{
    
    //Types of dependency
    public enum DependencyType
    {
        OPTIONAL,
        REQUIRED
    }
    
    //Check dependency
    public static void CheckDependency( String PluginName, DependencyType DependencyTypeOfPlugin )    
    {
        
        PluginManager plugins = Bukkit.getPluginManager();
        
        if ( plugins.isPluginEnabled( PluginName ) )
        {
            try {
                               
                plugins.enablePlugin( plugins.getPlugin( PluginName ) );
                
                DependencyFoundEvent event = new DependencyFoundEvent( plugins.getPlugin( PluginName ), DependencyTypeOfPlugin );
                Bukkit.getPluginManager().callEvent( event );
                
            } catch ( Exception ex ) {
                
                DependencyNotFoundEvent event = new DependencyNotFoundEvent( PluginName, DependencyTypeOfPlugin );
                Bukkit.getPluginManager().callEvent( event );
                
                AbortLoading( plugins.getPlugin( "Ancient" ), PluginName,DependencyTypeOfPlugin );
                
            }
        }

    }
    
    //Abor plugins start process
    private static void AbortLoading( Plugin plugin, String dependency, DependencyType type )
    {
        if ( type == DependencyType.REQUIRED )
        {
            
            System.out.println( Ancient.ConsoleBrand + "Disabling Ancient..." );
            System.out.println( Ancient.ConsoleBrand + dependency + " not found!" );
            Bukkit.getPluginManager().disablePlugin( plugin );
            
        } else {
            
            System.out.println( Ancient.ConsoleBrand + dependency + " not installed..." );
            
        }
    }
    
}
