package com.ancientshores.Ancient.Chat;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Chat.Events.WhisperMessage;

public class ChatEventLoader {
    
    public Ancient plugin;
    
    //Construction
    public ChatEventLoader( Ancient plugin ){
        
        this.plugin = plugin;
        
        if ( AncientChat.chatIsEnabled() ) 
        {
            registerEvents();
        }
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
