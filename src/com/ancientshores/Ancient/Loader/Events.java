package com.ancientshores.Ancient.Loader;

import com.ancientshores.Ancient.AncientNew;

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
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
}
