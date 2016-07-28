package com.ancientshores.Ancient.Chat;

import org.bukkit.entity.Player;


public class WMessage {
    
    protected String message;
    protected Player p;
    
    //Construction
    public WMessage( String message, Player p )
    {
        
        this.message = message;
        this.p = p;
        
    }
    
    //Get the formatted message
    public String getMessage()
    {
        
        return message;
        
    }
    
    
}
