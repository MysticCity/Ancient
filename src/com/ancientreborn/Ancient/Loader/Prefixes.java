package com.ancientreborn.Ancient.Loader;

import com.ancientreborn.Ancient.Ancient;

public class Prefixes {
    
    public Ancient plugin;
    
    //Construction
    public Prefixes( Ancient plugin )
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
