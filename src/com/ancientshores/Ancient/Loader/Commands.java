package com.ancientshores.Ancient.Loader;

import com.ancientshores.Ancient.AncientNew;

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
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
}
