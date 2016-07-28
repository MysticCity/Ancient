package com.ancientreborn.Ancient;

import org.bukkit.Bukkit;

public class CompatibilityTool {
    
    //Enumerator for ServerVersions
    public enum ServerVersion
    {
        //Version: Unbekannt
        VERSION_NONE, 
        
        //Verssion: 1.9.2
        VERSION_1_9_2,
        
        //Version: 1.9.4
        VERSION_1_9_4,
        
        //Version: 1.10.0
        VERSION_1_10_0,
        
    }
    
    //Return: Current server version as ServerVersion
    public static ServerVersion getSrvVersion()
    {
        
        String currentVersion = Bukkit.getVersion();
        
        //Current Version is 1.9.2
        if ( currentVersion.contains( "1.9.2" ) )
        {
            return ServerVersion.VERSION_1_9_2;
        }
        
        //Current Version is 1.9.4
        if ( currentVersion.contains( "1.9.4" ) )
        {
            return ServerVersion.VERSION_1_9_4;
        }
        
        //Current Version 1.10.x
        if ( currentVersion.contains( "1.10" ) )
        {
            return ServerVersion.VERSION_1_10_0;
        }
        
        return ServerVersion.VERSION_NONE;
        
    }
    
}
