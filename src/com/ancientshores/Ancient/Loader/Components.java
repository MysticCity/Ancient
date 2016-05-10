package com.ancientshores.Ancient.Loader;

import com.ancientshores.Ancient.AncientNew;

public class Components {
    
    public AncientNew plugin;
    
    //Construction
    public Components( AncientNew plugin )
    {
        
        this.plugin = plugin;
        
        loadComponents();
        
    }
    
    //Load all required components
    private void loadComponents()
    {
        try {
            /*                                                        HOW TO USE
             *                                               Just add everything to load.
             */
            
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
}
