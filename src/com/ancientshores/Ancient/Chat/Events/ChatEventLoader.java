package com.ancientshores.Ancient.Chat.Events;

import com.ancientshores.Ancient.Ancient;

public class ChatEventLoader {
    
    protected Ancient plugin;
    
    //Construction
    public ChatEventLoader( Ancient plugin ){
        
        this.plugin = plugin;
        
        registerEvents();
        
    }
    
    //Register all CategoryEvents
    private void registerEvents()
    {
        try{
            
            plugin.getServer().getPluginManager().registerEvents( new WhisperMessage(this) , plugin);
            
        } catch ( Exception ex ) {
            
            ex.printStackTrace();
            
        }
    }
    
}
