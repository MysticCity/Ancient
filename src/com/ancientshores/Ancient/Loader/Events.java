package com.ancientshores.Ancient.Loader;

import com.ancientshores.Ancient.Main.AncientNew;
import com.ancientshores.Ancient.Main.CompatibilityTool;
import org.bukkit.Bukkit;

public class Events {
 
    public AncientNew plugin;
    
    //Construction
    public Events( AncientNew plugin )
    {
        
        this.plugin = plugin;
        
        registerEvents();
        
    }
    
    //Register all required events
    private void registerEvents()
    {
        try {
            /*                                                      HOW TO USE
             *      plugin.getServer().getPluginManager().registerEvents( new <Listener Class>( this ) , plugin);
             */
            
            switch( CompatibilityTool.getSrvVersion() )
            {
                
                //Version unknown
                case VERSION_NONE:
                    
                    System.out.println( plugin.ConsoleBrand + "Unknow server system! Disabling." );
                    
                    Bukkit.getPluginManager().disablePlugin( plugin );
                    
                    break;
                    
                case VERSION_1_9_2:
                    
                    System.out.println( plugin.ConsoleBrand + "Using compatibility: 1.9.2" );
                    
                    /*
                     *      Load all classes/liseners eg. that require 1.9.2
                     */
                    
                    break;
                    
                case VERSION_1_9_4:
                    
                    System.out.println( plugin.ConsoleBrand + "Using compatibility: 1.9.4" );
                    
                    /*
                     *      Load all classes/liseners eg. that require 1.9.4
                     */
                    
                    break;
                    
                case VERSION_1_10_0:
                    
                    System.out.println( plugin.ConsoleBrand + "Using compatibility: 1.10.0" );
                    
                    /*
                     *      Load all classes/liseners eg. that require 1.10.0
                     */
                
                    break;
                    
            }
            
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
}
