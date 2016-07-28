package com.ancientreborn.Ancient.Backward;

import org.bukkit.Bukkit;

public class CompatTool 
{
    
    public enum ServerVersion
    {
        SERVER_1_10_X,
        UNKNOWN
    }
    
    public static ServerVersion GetServerVersion()
    {
        
        if ( Bukkit.getVersion().contains( "1.10" ) )
        {
            
            return ServerVersion.SERVER_1_10_X;
            
        }
        
        return ServerVersion.UNKNOWN;
        
    }
    
}
