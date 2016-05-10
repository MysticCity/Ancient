package com.ancientshores.Ancient.Loader;

import com.ancientshores.Ancient.AncientNew;

public class Prefixes {
    
    public AncientNew plugin;
    
    //Construction
    public Prefixes( AncientNew plugin )
    {
        
        this.plugin = plugin;
        
        loadPrefixes();
        
    }
    
    //Load prefix into the main-class
    private void loadPrefixes()
    {
        try {
            
            plugin.ConsoleBrand = plugin.config.getString( "ConsoleBrand" );

            plugin.ChatBrand = plugin.config.getString( "ChatBrand" ).replaceAll( "&" , "§" );
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
}
