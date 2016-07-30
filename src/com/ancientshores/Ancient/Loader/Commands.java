package com.ancientshores.Ancient.Loader;

import com.ancientshores.Ancient.Main.AncientNew;
import com.ancientshores.Ancient.Main.CompatibilityTool;
import org.bukkit.Bukkit;

public class Commands {
    
    public AncientNew plugin;
    
    //Construction
    public Commands( AncientNew plugin )
    {
     
        this.plugin = plugin;
        
        registerCommands();
        
    }
    
    //Register all events
    private void registerCommands()
    {
        try {
            
            /*                                                      HOW TO USE
             *  plugin.getCommand(<Command Name as String>).setExecutor( new <Executor Class>(this, <Command from config>) );
             */
            
            switch( CompatibilityTool.getSrvVersion() )
            {
                
                //Version unknown
                case VERSION_NONE:
                    
                    System.out.println( plugin.ConsoleBrand + "Unknown server system! Disabling." );
                    
                    Bukkit.getPluginManager().disablePlugin( plugin );
                    
                    break;
                    
                case VERSION_1_9_2:
                    
                    System.out.println( plugin.ConsoleBrand + "Using compatibility: 1.9.2" );
                    
                    /*
                     *      Load all classes/liseners that require 1.9.2
                     */
                    
                    break;
                    
                case VERSION_1_9_4:
                    
                    System.out.println( plugin.ConsoleBrand + "Using compatibility: 1.9.4" );
                    
                    /*
                     *      Load all classes/liseners that require 1.9.4
                     */
                    
                    break;
                    
                case VERSION_1_10_0:
                    
                    System.out.println( plugin.ConsoleBrand + "Using compatibility: 1.10.0" );
                    
                    /*
                     *      Load all classes/liseners that require 1.10.0
                     */
                
                    break;
                    
            }
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
}
